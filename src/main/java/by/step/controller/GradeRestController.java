package by.step.controller;


import by.step.model.Grade;
import by.step.model.Subject;
import by.step.service.AcademicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeRestController {

    private final AcademicService academicService;

    @PostMapping
    public String addGrade(@RequestBody Grade grade) {
        academicService.addGrade(grade);
        return "Оценка добавлена";
    }

    @DeleteMapping("/{gradeId}")
    public String removeGrade(@PathVariable int gradeId) {
        academicService.removeGrade(gradeId);
        return "Оценка удалена";
    }

    @GetMapping("/student/{studentId}")
    public List<Grade> grades(@PathVariable int studentId) {
        return academicService.getGradesByStudentId(studentId);
    }

    @GetMapping("/subject/{subject}")
    public List<Grade> getGradesBySubject(@PathVariable Subject subject){
        return academicService.getGradesBySubject(subject);
    }

    @GetMapping("/average/student/{studentId}")
    public Double getAverageByStudent(@PathVariable int studentId) {
        return academicService.getAverageScoreByStId(studentId);
    }

    @GetMapping("/average/subject/{subject}")
    public Double getAverageBySubject(@PathVariable Subject subject) {
        return academicService.getAverageBySubject(subject);
    }




}
