package cl.untec.springedumanager.controller;

import cl.untec.springedumanager.model.Course;
import cl.untec.springedumanager.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
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