package unit.br.unitnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unit.br.unitnetwork.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
