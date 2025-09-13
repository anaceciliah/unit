package unit.br.unitnetwork.service;

import org.springframework.stereotype.Service;
import unit.br.unitnetwork.dto.UserRequestDto;
import unit.br.unitnetwork.dto.UserResponseDto;
import unit.br.unitnetwork.entity.User;
import unit.br.unitnetwork.exception.DuplicateEmailException;
import unit.br.unitnetwork.exception.EmailNotRegisteredException;
import unit.br.unitnetwork.repository.UserRepository;
import unit.br.unitnetwork.utils.Strings;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto register (UserRequestDto user){
        if (userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateEmailException(
                    String.format(Strings.DUPLICATE_EMAIL, user.getEmail()));
        }

        User newUser = userRepository.save(toUser(user));

        return fromUser(newUser);

    }

    private User toUser(UserRequestDto user) {
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .message(user.getMessage())
                .photo(user.getPhoto())
                .active(true)
                .build();

    }
    private UserResponseDto fromUser(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .message(user.getMessage())
                .photo(user.getPhoto())
                .build();
    }


    public UserResponseDto getById(Long id) {
        var user = userRepository.getById(id);
        return fromUser(user);
    }

    public UserResponseDto editeUser(UserRequestDto user){
        return fromUser(userRepository.save(toUser(user)));
    }

    public UserResponseDto deleteUser(Long id){
        var user = userRepository.getById(id);
        user.setActive(false);
        return fromUser(userRepository.save(user));
    }


}
