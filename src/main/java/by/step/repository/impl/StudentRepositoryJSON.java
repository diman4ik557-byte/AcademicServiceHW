package by.step.repository.impl;

import by.step.model.Student;
import by.step.repository.StudentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Repository
public class StudentRepositoryJSON implements StudentRepository {

    @Setter
    @Value("#{'${data.files}'.split(',')[0]}")
    private String data;

    @Setter
    private SimpleDateFormat dF;

    private void rewriteData(List<Student> students){
        try {
            newMapper().writeValue(new File(data),students);
        } catch (IOException e){
            throw new RuntimeException("Ошибка при записи информации студента", e);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            students = newMapper().readValue(new File(data),
                    new TypeReference<List<Student>>() {
                    });
        }catch (IOException e){
            rewriteData(Collections.emptyList());
        }
        return students;
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
