package unit.br.unitnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unit.br.unitnetwork.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserId(Long userId);

    Optional<Post> findByIdAndUserId(Long id, Long userId);
}
