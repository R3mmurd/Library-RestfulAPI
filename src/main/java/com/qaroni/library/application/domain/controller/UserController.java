package com.qaroni.library.application.domain.controller;

import com.qaroni.library.application.domain.dto.FullBookDto;
import com.qaroni.library.application.domain.dto.LoginDto;
import com.qaroni.library.application.domain.dto.LoginResponseDto;
import com.qaroni.library.application.domain.dto.SignUpDto;
import com.qaroni.library.application.domain.jwt.JwtUtil;
import com.qaroni.library.application.domain.repository.UserRepository;
import com.qaroni.library.application.domain.entity.User;
import com.qaroni.library.application.infrastructure.email.EmailService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Tag(name = "Login", description = "Logins a user")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Not allowed to login",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(authentication.getName());
        String token = jwtUtil.createToken(user);

        LoginResponseDto loginResponseDto = new LoginResponseDto("User login successfully!", token);

        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    @Tag(name = "Signup", description = "Registers a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "User already exists",
                    content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already exist!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setFullName(signUpDto.getFullName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setRole(signUpDto.getRole());
        userRepository.save(user);

        emailService.sendEmail(user.getEmail(), "Registration result", "You have been registered successfully!");

        return new ResponseEntity<>("User is registered successfully!", HttpStatus.OK);
    }
}
