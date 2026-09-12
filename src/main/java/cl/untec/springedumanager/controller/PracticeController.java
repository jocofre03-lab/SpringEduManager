package cl.untec.springedumanager.controller;

import cl.untec.springedumanager.model.Course;
import cl.untec.springedumanager.model.Practice;
import cl.untec.springedumanager.repository.CourseRepository;
import cl.untec.springedumanager.repository.PracticeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PracticeController {

    private final PracticeRepository practiceRepository;
    private final CourseRepository courseRepository;

    public PracticeController(PracticeRepository practiceRepository, CourseRepository courseRepository) {
        this.practiceRepository = practiceRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/practices")
    public String listPractices(Model model) {

        if (practiceRepository.count() == 0) {
            Course javaCourse = courseRepository.findAll().stream()
                    .filter(c -> c.getCode().equals("JAVA-001"))
                    .findFirst()
                    .orElse(null);

            if (javaCourse != null) {
                Practice practice1 = new Practice();
                practice1.setTitle("Practice 1: Variables");
                practice1.setDescription("Introduction to Java variables and data types");
                practice1.setDueDate(LocalDate.now().plusDays(7));
                practice1.setCourse(javaCourse);
                practiceRepository.save(practice1);
            }
        }

        List<Practice> practices = practiceRepository.findAll();
        model.addAttribute("practiceList", practices);
        return "practices";
    }
}
