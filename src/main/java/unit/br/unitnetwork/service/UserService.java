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
import static unit.br.unitnetwork.utils.Strings.ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    public UserResponseDto register(UserRequestDto user, MultipartFile photo) {
        validateRegisterRequest(user);

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException(
                    String.format(Strings.DUPLICATE_EMAIL, user.getEmail()));
        }

        User userToSave = toUser(user);
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        applyPhotoIfPresent(userToSave, photo);

        User newUser = userRepository.save(userToSave);
        return fromUser(newUser);
    }

    public UserResponseDto getById(Long id) {
         return fromUser(findUserById(id));
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
            throw new IllegalArgumentException("UserRequestDto nao pode ser nulo");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID nao pode ser nulo");
        }

        User existingUser = findUserById(id);
        boolean hasChanges = false;

        if (user.getName() != null && !user.getName().equals(existingUser.getName())) {
            existingUser.setName(user.getName());
            hasChanges = true;
        }

        if (user.getEmail() != null && !user.getEmail().isBlank() && !user.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new IllegalArgumentException(String.format(Strings.DUPLICATE_EMAIL, user.getEmail()));
            }
            existingUser.setEmail(user.getEmail());
            hasChanges = true;
        }

        if (user.getPhoto() != null && !user.getPhoto().equals(existingUser.getPhoto())) {
            existingUser.setPhoto(user.getPhoto());
            hasChanges = true;
        }

        if (user.getMessage() != null && !user.getMessage().equals(existingUser.getMessage())) {
            existingUser.setMessage(user.getMessage());
            hasChanges = true;
        }

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            hasChanges = true;
        }

        if (hasChanges) {
            existingUser = userRepository.save(existingUser);
        }

        return fromUser(existingUser);
    }

    public UserResponseDto deleteUser(Long id){
        User user = findUserById(id);
        user.setActive(false);
        return fromUser(userRepository.save(user));
    }

    private void validateRegisterRequest(UserRequestDto user) {
        if (user == null) {
            throw new IllegalArgumentException("UserRequestDto nao pode ser nulo");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email nao pode ser vazio");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Senha nao pode ser vazia");
        }
    }

    private void applyPhotoIfPresent(User user, MultipartFile photo) {
        if (photo != null && !photo.isEmpty()) {
            user.setPhoto(fileService.saveFile(photo));
        }
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound(String.format(ID_NOT_FOUND, id)));
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
