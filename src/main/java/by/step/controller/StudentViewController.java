package by.step.controller;


import by.step.model.Student;
import by.step.service.AcademicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentViewController {

    private final AcademicService academicService;

    @GetMapping
    public String getAllStudents(Model model) {
        List<Student> students = academicService.getAllStudents();

        // ОТЛАДКА
        System.out.println("========== StudentViewController ==========");
        System.out.println("Получено студентов: " + students.size());
        for (Student s : students) {
            System.out.println("  - " + s.getId() + ": " + s.getName() + " (" + s.getEmail() + ")");
        }
        System.out.println("===========================================");

        model.addAttribute("students", students);
        return "students/list";
    }
    @GetMapping("/{id}")
    public String getStudentById(@PathVariable int id, Model model) {
        Student student = academicService.findStudentById(id);
        double averageScore = academicService.getAverageScoreByStId(id);
        List<?> grades = academicService.getGradesByStudentId(id);
        model.addAttribute("student", student);
        model.addAttribute("grades", grades);
        model.addAttribute("averageScore", averageScore);
        return "students/view";
    }

    @PostMapping
    public String addStudent(@ModelAttribute Student student) {
        academicService.addStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/remove")
    public String removeStudent(@PathVariable int id) {
        academicService.removeStudent(id);
        return "redirect:/students";
    }

    @GetMapping("/top")
    public String getTopStudents(@RequestParam(defaultValue = "5") int limit, Model model) {
        List<Student> topStudents = academicService.getTopStudents(limit);
        model.addAttribute("students", topStudents);
        model.addAttribute("limit", limit);
        return "students/top";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/create";
    }

    @GetMapping("/{id}/delete")
    public String deleteStudent(@PathVariable int id) {
        academicService.removeStudent(id);
        return "redirect:/students";
    }


}
