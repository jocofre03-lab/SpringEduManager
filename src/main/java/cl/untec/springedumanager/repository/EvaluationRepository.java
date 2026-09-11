package cl.untec.springedumanager.repository;

import cl.untec.springedumanager.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}