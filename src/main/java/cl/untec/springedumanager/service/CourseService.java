package cl.untec.springedumanager.service;

import cl.untec.springedumanager.exception.CourseNotFoundException;
import cl.untec.springedumanager.model.Course;
import cl.untec.springedumanager.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
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
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
    }

    public Course createCourse(String name, String code) {
        Course course = new Course();
        course.setName(name);
        course.setCode(code);
        return courseRepository.save(course);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        Course course = getCourseById(id);
        course.setName(updatedCourse.getName());
        course.setCode(updatedCourse.getCode());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}