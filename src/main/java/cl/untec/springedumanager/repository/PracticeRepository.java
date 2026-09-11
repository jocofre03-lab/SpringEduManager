package cl.untec.springedumanager.repository;

import cl.untec.springedumanager.model.Practice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeRepository extends JpaRepository<Practice, Long> {
}