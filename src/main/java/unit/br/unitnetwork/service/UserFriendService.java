package unit.br.unitnetwork.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import unit.br.unitnetwork.dto.UserFriendRequestDto;
import unit.br.unitnetwork.dto.UserFriendResponseDto;
import unit.br.unitnetwork.entity.User;
import unit.br.unitnetwork.entity.UserFriend;
import unit.br.unitnetwork.exception.UserNotFoundException;
import unit.br.unitnetwork.repository.UserFriendRepository;
import unit.br.unitnetwork.repository.UserRepository;
import unit.br.unitnetwork.utils.Strings;

@Service
public class UserFriendService {

    private final UserFriendRepository userFriendRepository;
    private final UserRepository userRepository;

    public UserFriendService(UserFriendRepository userFriendRepository, UserRepository userRepository) {
        this.userFriendRepository = userFriendRepository;
        this.userRepository = userRepository;
    }

public UserFriendResponseDto register (UserFriendRequestDto userFriendRequestDto){
      User friend = userRepository.findById(userFriendRequestDto.getFriendId())
              .orElseThrow(() -> new UserNotFoundException(Strings.USER_NOT_FOUND));

      var savedFriend = userFriendRepository.save(UserFriend.builder()
              .userId(userFriendRequestDto.getUserId())
              .user(friend)

              .build());

      return UserFriendResponseDto.builder()
              .id(savedFriend.getId())
              .userId(savedFriend.getUserId())
              .friendId(savedFriend.getUser().getId())
              .build();


}
}
