package unit.br.unitnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unit.br.unitnetwork.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsByEmail(String email);
    User findByEmail(String email);

    User getById(Long id);

    List<User> id(Long id);
}
