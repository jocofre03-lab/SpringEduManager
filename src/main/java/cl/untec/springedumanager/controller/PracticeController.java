package cl.untec.springedumanager.controller;

import cl.untec.springedumanager.model.Course;
import cl.untec.springedumanager.model.Practice;
import cl.untec.springedumanager.service.EnrollmentService;
import cl.untec.springedumanager.service.PracticeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PracticeController {

    private final PracticeService practiceService;
    private final EnrollmentService enrollmentService;

    public PracticeController(PracticeService practiceService, EnrollmentService enrollmentService) {
        this.practiceService = practiceService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/practices")
    public String listPractices(Model model, Authentication authentication) {

        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        List<Practice> practices;
        if (isAdmin) {
            practices = practiceService.getAllPractices();
        } else {
            String email = authentication.getName();
            List<Course> myCourses = enrollmentService.getCoursesForStudentEmail(email);
            practices = practiceService.getPracticesForCourses(myCourses);
        }

        model.addAttribute("practiceList", practices);
        return "practices";
    }
}