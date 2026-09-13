package cl.untec.springedumanager.service;

import cl.untec.springedumanager.model.Course;
import cl.untec.springedumanager.model.Practice;
import cl.untec.springedumanager.repository.CourseRepository;
import cl.untec.springedumanager.repository.PracticeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PracticeService {

    private final PracticeRepository practiceRepository;
    private final CourseRepository courseRepository;

    public PracticeService(PracticeRepository practiceRepository, CourseRepository courseRepository) {
        this.practiceRepository = practiceRepository;
        this.courseRepository = courseRepository;
    }

    public List<Practice> getAllPractices() {
        ensureSeedData();
        return practiceRepository.findAll();
    }

    public List<Practice> getPracticesForCourses(List<Course> courses) {
        ensureSeedData();
        return practiceRepository.findByCourseIn(courses);
    }

    private void ensureSeedData() {
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
    }
}