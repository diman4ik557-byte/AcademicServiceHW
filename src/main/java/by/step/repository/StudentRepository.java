package by.step.repository;


import by.step.model.Student;

import java.util.List;

public interface StudentRepository {


    List<Student> getAllStudents();
    Student findByID(int id);
    void addStudent (Student student);
    void removeStudent(int id);
}
