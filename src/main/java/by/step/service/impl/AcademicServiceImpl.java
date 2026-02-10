package by.step.service.impl;

import by.step.model.Grade;
import by.step.model.Student;
import by.step.model.Subject;
import by.step.repository.GradeRepository;
import by.step.repository.StudentRepository;
import by.step.service.AcademicService;

import java.util.List;

public class AcademicServiceImpl implements AcademicService {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    public AcademicServiceImpl(StudentRepository studentRepository,
                               GradeRepository gradeRepository){
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public void addStudent(Student student) {
        studentRepository.addStudent(student);

    }

    @Override
    public void removeStudent(int studentId) {
        studentRepository.removeStudent(studentId);

    }

    @Override
    public Student findStudentById(int id) {
        return studentRepository.findByID(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();

    }

    @Override
    public void addGrade(Grade grade) {
        Student student = studentRepository.findByID(grade.getStudentId());
        if(student == null){
            throw new IllegalArgumentException("Студента с Id"
                    + grade.getStudentId() + " не существует");
        }
        gradeRepository.addGrade(grade);
    }

    @Override
    public void removeGrade(int gradeId) {
        gradeRepository.removeGrade(gradeId);

    }

    @Override
    public List<Grade> getGradesByStudentId(int studentId) {
        return gradeRepository.getGradesByStId(studentId);

    }

    @Override
    public List<Grade> getGradesBySubject(Subject subject) {
        return gradeRepository.getGradesBySubject(subject);

    }

    @Override
    public double getAverageScore(String studentName) {
        Student student = studentRepository.getAllStudents().stream()
                .filter(st ->
                        st.getName().equalsIgnoreCase(studentName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Студент \"" + studentName +"\" не найден"));
                return gradeRepository.getAverageByStId(student.getId());

    }

    @Override
    public double getAverageScoreByStId(int studentId) {
        return gradeRepository.getAverageByStId(studentId);

    }

    @Override
    public double getAverageBySubject(Subject subject) {
        return gradeRepository.getAverageBySubject(subject);

    }

    @Override
    public List<Student> getTopStudents(int limit) {
        return studentRepository.getAllStudents().stream()
                .sorted((s1,s2) -> {
                    double avg1 = gradeRepository.getAverageByStId(s1.getId());
                    double avg2 = gradeRepository.getAverageByStId(s2.getId());
                    return  Double.compare(avg2,avg1);
                })
                .limit(limit)
                .toList();
    }
}
