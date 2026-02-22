package by.step.controller;

import by.step.model.Student;
import by.step.service.AcademicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentRestController {

    private final AcademicService academicService;

    @PostMapping
    public String addStudent(@RequestBody Student student){
        academicService.addStudent(student);
        return "Студент добавлен\n(+ " +student +")";
    }

    @DeleteMapping("/{id}")
    public String removeStudent(@PathVariable int id) {
        academicService.removeStudent(id);
        return "Студент удалён \n(- " +academicService.findStudentById(id) +")";
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return academicService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student findById(@PathVariable("id") int id){
        return academicService.findStudentById(id);
    }

    @GetMapping("/param-id")
    public Student findByIdInParam(
            @RequestParam(name = "id",
                    required = false) Integer id) {
        return id != null
                ? academicService.findStudentById(id)
                : null;
    }

    @GetMapping("/top")
    public List<Student> getTopStudents(@RequestParam(defaultValue = "5") int limit) {
        return academicService.getTopStudents(limit);
    }

}
