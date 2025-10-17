package unit.br.unitnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import unit.br.unitnetwork.dto.UserFriendRequestDto;
import unit.br.unitnetwork.dto.UserFriendResponseDto;
import unit.br.unitnetwork.dto.UserRequestDto;
import unit.br.unitnetwork.dto.UserResponseDto;
import unit.br.unitnetwork.service.UserFriendService;
import unit.br.unitnetwork.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

     private final UserService userService;
    private final UserFriendService userFriendService;


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

    @PostMapping("friend")
    public ResponseEntity<UserFriendResponseDto> newFriend(@RequestBody UserFriendRequestDto userFriendRequestDto) {

        return new ResponseEntity<>(userFriendService.register(userFriendRequestDto), HttpStatus.CREATED);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<UserResponseDto> register (
            @RequestPart("user") UserRequestDto user,
            @RequestPart(value = "photo", required = false)MultipartFile photo){
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }











}







