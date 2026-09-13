package cl.untec.springedumanager.controller;

import cl.untec.springedumanager.model.Enrollment;
import cl.untec.springedumanager.service.CourseService;
import cl.untec.springedumanager.service.EnrollmentService;
import cl.untec.springedumanager.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentController(EnrollmentService enrollmentService,
                                StudentService studentService,
                                CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping("/enrollments")
    public String listEnrollments(Model model) {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        model.addAttribute("enrollmentList", enrollments);
        return "enrollments";
    }

    @GetMapping("/enrollments/new")
    public String showEnrollmentForm(Model model) {
        model.addAttribute("studentList", studentService.getAllStudents());
        model.addAttribute("courseList", courseService.getAllCourses());
        return "enrollment-form";
    }

    @PostMapping("/enrollments/new")
    public String createEnrollment(@RequestParam Long studentId, @RequestParam Long courseId) {
        enrollmentService.enrollStudent(studentId, courseId);
        return "redirect:/enrollments";
    }
}
