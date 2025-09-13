package unit.br.unitnetwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unit.br.unitnetwork.dto.UserRequestDto;
import unit.br.unitnetwork.dto.UserResponseDto;
import unit.br.unitnetwork.entity.User;
import unit.br.unitnetwork.exception.EmailNotRegisteredException;
import unit.br.unitnetwork.repository.UserRepository;
import unit.br.unitnetwork.service.UserService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

     private final UserService userService;


    public UserController(UserRepository userRepository, UserService userService) {

        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto user) {
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getByID(@PathVariable  long id  ) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto user) {
        return new ResponseEntity<>(userService.editeUser(user), HttpStatus.OK);
    }

    @PutMapping("delete/{id}")
    public ResponseEntity<UserResponseDto> deleteUser (@PathVariable  long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }










}







