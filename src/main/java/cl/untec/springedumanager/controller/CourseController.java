package cl.untec.springedumanager.controller;


import cl.untec.springedumanager.model.Course;
import cl.untec.springedumanager.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public String listCourses(Model model) {

        // Guardamos cursos de prueba solo si la base de datos está vacía
        if (courseRepository.count() == 0) {
            Course java = new Course();
            java.setName("Java");
            java.setCode("JAVA-001");
            courseRepository.save(java);

            Course spring = new Course();
            spring.setName("Spring Boot");
            spring.setCode("SPRING-001");
            courseRepository.save(spring);
        }

        // Leemos todos los cursos desde la base de datos
        List<Course> courses = courseRepository.findAll();

        model.addAttribute("courseList", courses);
        return "courses";
    }
}