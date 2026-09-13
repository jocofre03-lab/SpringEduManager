package cl.untec.springedumanager.repository;

import cl.untec.springedumanager.model.Enrollment;
import cl.untec.springedumanager.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
      List<Enrollment> findByStudent(Student student);
}