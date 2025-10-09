package unit.br.unitnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unit.br.unitnetwork.entity.UserFriend;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {


}
