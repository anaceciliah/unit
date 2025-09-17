package unit.br.unitnetwork.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import unit.br.unitnetwork.dto.AuthRequestDto;
import unit.br.unitnetwork.dto.AuthResponseDto;
import unit.br.unitnetwork.dto.UserRequestDto;
import unit.br.unitnetwork.dto.UserResponseDto;
import unit.br.unitnetwork.entity.User;
import unit.br.unitnetwork.exception.CredenciaisInvalidasException;
import unit.br.unitnetwork.service.TokenService;
import unit.br.unitnetwork.service.UserService;
import unit.br.unitnetwork.utils.Strings;

import java.util.Arrays;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto body) {
        User user = userService.toUser(userService.getUserCompletByEmail(body.email()));

        if (user == null || !passwordEncoder.matches(body.password(), user.getPassword())) {
            throw new CredenciaisInvalidasException(Strings.DUPLICATE_EMAIL);
        }


        return getToken(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto body){
        return new ResponseEntity<>(userService.register(body), HttpStatus.CREATED);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;

        System.out.println(Arrays.toString(cookies));

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null) {
            String isValid = tokenService.validateToken(token);
            if (isValid != null) {
                return ResponseEntity.ok(true);
            }
        }
        return ResponseEntity.ok(false);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.setHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().build();
    }

    private ResponseEntity<AuthResponseDto> getToken(User newUser) {
        String token = this.tokenService.generateToken(newUser);

        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(3600 * 5)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body(new AuthResponseDto(token));
    }
}
