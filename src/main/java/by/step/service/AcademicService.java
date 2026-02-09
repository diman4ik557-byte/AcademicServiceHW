package by.step.service;

import by.step.model.Grade;
import by.step.model.Student;
import by.step.model.Subject;

import java.util.List;

public interface AcademicService {

    void addStudent(Student student);
    void removeStudent(int studentId);
    Student findStudentById(int id);
    List<Student> getAllStudents();

    void addGrade(Grade grade);
    void removeGrade(int gradeId);
    List<Grade> getGradesByStudentId(int studentId);
    List<Grade> getGradesBySubject(Subject subject);

    double getAverageScore(String studentName);
    double getAverageScoreByStId(int studentId);
    double getAverageBySubject(Subject subject);

    List<Student> getTopStudents(int limit);
}
