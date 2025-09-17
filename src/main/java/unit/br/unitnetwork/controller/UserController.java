package unit.br.unitnetwork.controller;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserController {

     private final UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getByID(@PathVariable  long id  ) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponseDto> getByEmail(@RequestParam String email) {
        return new ResponseEntity<>(userService.getByEmail(email), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto user, @RequestParam Long id) {
        return new ResponseEntity<>(userService.editeUser(user, id), HttpStatus.OK);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<UserResponseDto> deleteUser (@PathVariable  long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }










}







