package by.step.repository.impl;

import by.step.model.Grade;
import by.step.model.Subject;
import by.step.repository.GradeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class GradeRepositoryJSON implements GradeRepository {

    @Setter
    private String data;

    @Setter
    private SimpleDateFormat dF;

    @Override
    public List<Grade> getAllGrades() {
        try {
            File file = new File(data);
            if (!file.exists()){
                newMapper().writeValue(file,Collections.emptyList());
                return new ArrayList<>();
            }
            return newMapper().readValue(file,
                    new TypeReference<List<Grade>>() {});
        } catch (IOException e){
            throw new RuntimeException("Ошибка при чтении оценок", e);
        }
    }

    @Override
    public List<Grade> getGradesByStId(int studentId) {
        return getAllGrades().stream()
                .filter(grade -> grade.getStudentId() == studentId)
                .toList();
    }

    @Override
    public List<Grade> getGradesBySubject(Subject subject) {
        return getAllGrades().stream()
                .filter(grade -> grade.getSubject() == subject)
                .toList();
    }

    @Override
    public void addGrade(Grade grade) {
        if (grade == null){
            throw new IllegalArgumentException("Оценка не может быть null");
        }
        if(grade.getScore() < 0 || grade.getScore() > 10){
            throw new IllegalArgumentException("Оценка должна быть от 0 до 10");
        }
        if(grade.getSubject() == null){
            throw new IllegalArgumentException("Предмет не может быть null");
        }
        List<Grade> grades = getAllGrades();
        grades.add(grade);
        rewriteData(grades);
    }

    @Override
    public void removeGrade(int gradeId) {
        List<Grade> grades = getAllGrades();
        if (gradeId < 0 || gradeId >= grades.size()){
            throw new IllegalArgumentException("Неверный Id оценки (" +gradeId +")");
        }
        grades.remove(gradeId);
        rewriteData(grades);
    }

    @Override
    public double getAverageByStId(int studentId) {
        return getGradesByStId(studentId).stream()
                .mapToInt(Grade::getScore)
                .average()
                .orElse(0.0);
    }

    @Override
    public double getAverageBySubject(Subject subject) {
        return getGradesBySubject(subject).stream()
                .mapToInt(Grade::getScore)
                .average()
                .orElse(0.0);
    }


    private void rewriteData(List<Grade> gradles) {
        try {
            newMapper().writeValue(new File(data), gradles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectMapper newMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(dF);
        mapper.setLocale(Locale.ENGLISH);
        mapper.registerModule(new JSR310Module());
        return mapper;
    }
}