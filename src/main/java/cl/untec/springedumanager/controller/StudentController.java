package cl.untec.springedumanager.controller;

import cl.untec.springedumanager.model.Student;
import cl.untec.springedumanager.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("studentList", students);
        return "students";
    }

    @GetMapping("/students/new")
    public String showRegistrationForm() {
        return "student-form";
    }

    @PostMapping("/students/new")
    public String registerStudent(@RequestParam String firstName,
                                  @RequestParam String lastName,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  Model model) {

        if (studentService.emailExists(email)) {
            model.addAttribute("errorMessage", "A student with this email is already registered.");
            return "student-form";
        }

        studentService.registerStudent(firstName, lastName, email, password);
        return "redirect:/students";
    }
}