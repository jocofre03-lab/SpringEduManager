package cl.untec.springedumanager.repository;

import cl.untec.springedumanager.model.Evaluation;
import cl.untec.springedumanager.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByStudent(Student student);
}