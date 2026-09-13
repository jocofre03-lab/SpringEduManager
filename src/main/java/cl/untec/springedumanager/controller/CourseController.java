package cl.untec.springedumanager.controller;

import cl.untec.springedumanager.model.Course;
import cl.untec.springedumanager.service.CourseService;
import cl.untec.springedumanager.service.EnrollmentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public CourseController(CourseService courseService, EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/courses")
    public String listCourses(Model model, Authentication authentication) {

        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        List<Course> courses;
        if (isAdmin) {
            courses = courseService.getAllCourses();
        } else {
            String email = authentication.getName();
            courses = enrollmentService.getCoursesForStudentEmail(email);
        }

        model.addAttribute("courseList", courses);
        return "courses";
    }

    @GetMapping("/courses/new")
    public String showCourseForm() {
        return "course-form";
    }

    @PostMapping("/courses/new")
    public String createCourse(@RequestParam String name, @RequestParam String code) {
        courseService.createCourse(name, code);
        return "redirect:/courses";
    }
}