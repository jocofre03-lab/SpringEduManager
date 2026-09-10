package cl.untec.springedumanager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class CourseController {

    @GetMapping("/courses")
    public String listCourses(Model model) {
        List<String> courses = List.of("Java", "Spring Boot", "Base de Datos");
        model.addAttribute("courseList", courses);
        return "courses";

    }
}
