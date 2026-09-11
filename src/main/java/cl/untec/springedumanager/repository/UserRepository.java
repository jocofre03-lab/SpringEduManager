package cl.untec.springedumanager.repository;

import cl.untec.springedumanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}