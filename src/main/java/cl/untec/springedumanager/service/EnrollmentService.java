package cl.untec.springedumanager.service;

import cl.untec.springedumanager.model.Course;
import cl.untec.springedumanager.model.Enrollment;
import cl.untec.springedumanager.model.Student;
import cl.untec.springedumanager.repository.CourseRepository;
import cl.untec.springedumanager.repository.EnrollmentRepository;
import cl.untec.springedumanager.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment enrollStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("ACTIVE");
        return enrollmentRepository.save(enrollment);
    }

    public List<Course> getCoursesForStudentEmail(String email) {
        return studentRepository.findByEmail(email)
                .map(student -> enrollmentRepository.findByStudent(student).stream()
                        .map(Enrollment::getCourse)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}