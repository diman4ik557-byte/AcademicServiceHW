package by.step.repository.impl;

import by.step.model.Student;
import by.step.repository.StudentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class StudentRepositoryJSON implements StudentRepository {

    @Setter
    @Value("#{'${data.files}'.split(',')[0]}")
    private String data;

    private String dataPath;

    @Setter
    private SimpleDateFormat dF;

    @Autowired
    public StudentRepositoryJSON(@Value("${app.date.format}") String pattern) {
        this.dF = new SimpleDateFormat(pattern);
    }

    @PostConstruct
    public void init() {
        this.dataPath = Objects.requireNonNull(
                Thread.currentThread().getContextClassLoader().getResource(data)
        ).getPath();
    }


    private void rewriteData(List<Student> students){
        try {
            newMapper().writeValue(new File(dataPath), students);  // используем dataPath
        } catch (IOException e){
            throw new RuntimeException("Ошибка при записи информации студента", e);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        try {
            File file = new File(dataPath);
            if (!file.exists()) {
                System.out.println("файл не найден");
                return new ArrayList<>();
            }
            List<Student> students = newMapper().readValue(file, new TypeReference<List<Student>>() {});
            System.out.println("Прочитано студентов: " + students.size());
            return students;
        }
        catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Student findByID(int id) {
        return getAllStudents().stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }


    @Override
    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Студент не может быть null");
        }
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email студетна не может быть null");
        }
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Имя студента не может быть null");
        }
        List<Student> students = getAllStudents();
        students.add(student);
        rewriteData(students);
    }

    @Override
    public void removeStudent(int id) {
        List<Student> students = getAllStudents();
        boolean removed = students.removeIf(student ->
                student.getId() == id);
        if (removed){
            rewriteData(students);
        } else {
            throw new RuntimeException("Студента с айди \"" +id +"\" не существует");
        }
    }

    private ObjectMapper newMapper(){
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(dF);
        mapper.setLocale(Locale.ENGLISH);
        mapper.registerModule(new JSR310Module());
        return mapper;
    }
}
