package cl.untec.springedumanager.controller;

import cl.untec.springedumanager.model.Student;
import cl.untec.springedumanager.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = studentRepository.findAll();
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
                                  Model model) {

        if (studentRepository.existsByEmail(email)) {
            model.addAttribute("errorMessage", "A student with this email is already registered.");
            return "student-form";
        }

        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        studentRepository.save(student);
        return "redirect:/students";
    }

}
