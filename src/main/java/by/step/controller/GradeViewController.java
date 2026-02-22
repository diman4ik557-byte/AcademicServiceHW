package by.step.controller;

import by.step.model.Grade;
import by.step.model.Student;
import by.step.model.Subject;
import by.step.service.AcademicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/grades")
@RequiredArgsConstructor
public class GradeViewController {

    private final AcademicService academicService;

    @GetMapping("/student/{studentId}/add")
    public String showAddGradeForm(@PathVariable int studentId, Model model) {
        Student student = academicService.findStudentById(studentId);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        model.addAttribute("subjects", Subject.values());
        return "grades/create";
    }

    @PostMapping("/student/{studentId}")
    public String addGrade(@PathVariable int studentId,
                           @RequestParam Subject subject,
                           @RequestParam int score) {
        Grade grade = Grade.builder()
                .studentId(studentId)
                .subject(subject)
                .score(score)
                .build();
        academicService.addGrade(grade);
        return "redirect:/students/" + studentId;
    }

    @GetMapping("/delete/{studentId}/{gradeIndex}")
    public String deleteGrade(@PathVariable int studentId, @PathVariable int gradeIndex) {
            academicService.removeGrade(gradeIndex);
        return "redirect:/students/" + studentId;
    }

}
