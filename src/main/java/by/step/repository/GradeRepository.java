package by.step.repository;

import by.step.model.Grade;
import by.step.model.Subject;

import java.util.List;

public interface GradeRepository {

 List<Grade> getAllGrades();
 List<Grade> getGradesByStId(int studentId);
 List<Grade> getGradesBySubject(Subject subject);

 void addGrade(Grade grade);
 void removeGrade(int gradeId);
 double getAverageByStId(int studentId);
 double getAverageBySubject(Subject subject);


}
