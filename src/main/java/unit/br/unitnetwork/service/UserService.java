package unit.br.unitnetwork.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unit.br.unitnetwork.dto.UserCompleteResponseDto;
import unit.br.unitnetwork.dto.UserRequestDto;
import unit.br.unitnetwork.dto.UserResponseDto;
import unit.br.unitnetwork.entity.User;
import unit.br.unitnetwork.exception.DuplicateEmailException;
import unit.br.unitnetwork.exception.EmailNotRegisteredException;
import unit.br.unitnetwork.exception.UserNotFound;
import unit.br.unitnetwork.repository.UserRepository;
import unit.br.unitnetwork.utils.Strings;

import java.util.List;

import static unit.br.unitnetwork.utils.Strings.EMAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    public UserResponseDto register (UserRequestDto user, MultipartFile photo) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateEmailException(
                    String.format(Strings.DUPLICATE_EMAIL, user.getEmail()));
        }

       var userToSave = toUser(user);
        String filename = fileService.saveFile(photo);
        userToSave.setPhoto(filename);

        User newUser = userRepository.save(userToSave);
        return fromUser(newUser);

    }

    public UserResponseDto getById(Long id) {
         return fromUser(userRepository.findById(id).orElseThrow(() -> new UserNotFound(String.format(Strings.ID_NOT_FOUND, id))));
    }

    public  UserResponseDto getByEmail(String email) {
        return fromUser(userRepository.findByEmail(email).orElseThrow(() -> new EmailNotRegisteredException(String.format(EMAIL_NOT_FOUND, email))));
    }

    public UserCompleteResponseDto getUserCompletByEmail(String email) {
        return userToUserCompleteDto(userRepository.findByEmail(email).orElseThrow(() -> new EmailNotRegisteredException(String.format(EMAIL_NOT_FOUND, email))));
    }

    public List<UserResponseDto> getAll(){
        return userRepository.findAll().stream()
                .map(this::fromUser)
                .toList();
    }

    public UserResponseDto editeUser(UserRequestDto user, Long id) {
        if (user == null) {
            throw new IllegalArgumentException("UserRequestDto não pode ser nulo");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }

        User existingUser = toUser(getById(id));

        boolean hasChanges = false;

        if (user.getName() != null) {
            if (!user.getName().equals(existingUser.getName())) {
                existingUser.setName(user.getName());
                hasChanges = true;
            }
        }

        if (user.getEmail() != null) {
            if (!user.getEmail().equals(existingUser.getEmail())) {
                if (userRepository.existsByEmail(user.getEmail())) {
                    throw new IllegalArgumentException(String.format(Strings.DUPLICATE_EMAIL, user.getEmail()));
                }
                existingUser.setEmail(user.getEmail());
                hasChanges = true;
            }
        }

        if (user.getPhoto() != null) {
            if (!user.getPhoto().equals(existingUser.getPhoto())) {
                existingUser.setPhoto(user.getPhoto());
                hasChanges = true;
            }
        }

        if (user.getMessage() != null) {
            if (!user.getMessage().equals(existingUser.getMessage())) {
                existingUser.setMessage(user.getMessage());
                hasChanges = true;
            }
        }

        if (hasChanges) {
            existingUser = userRepository.save(existingUser);
        }

        return fromUser(existingUser);
    }

    public UserResponseDto deleteUser(Long id){
        User user = toUser(getById(id));
        user.setActive(false);
        return fromUser(userRepository.save(user));
    }

    public User toUser(UserRequestDto user) {
        return modelMapper.map(user, User.class);

    }

    public User toUser(UserResponseDto userResponseDto) {
        return modelMapper.map(userResponseDto, User.class);
    }
    public User toUser(UserCompleteResponseDto userCompleteResponseDto) {
        return modelMapper.map(userCompleteResponseDto, User.class);
    }

    private UserResponseDto fromUser(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }
    public UserCompleteResponseDto userToUserCompleteDto(User user) {
        return modelMapper.map(user, UserCompleteResponseDto.class);
    }






}
