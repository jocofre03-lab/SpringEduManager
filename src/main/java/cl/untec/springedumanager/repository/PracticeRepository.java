package cl.untec.springedumanager.repository;

import cl.untec.springedumanager.model.Course;
import cl.untec.springedumanager.model.Practice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PracticeRepository extends JpaRepository<Practice, Long> {
    List<Practice> findByCourseIn(List<Course> courses);
}