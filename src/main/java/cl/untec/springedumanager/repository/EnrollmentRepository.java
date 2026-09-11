package cl.untec.springedumanager.repository;

import cl.untec.springedumanager.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}