package spring.projects.e_commerce.website.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.projects.e_commerce.website.dto.LoginDto;
import spring.projects.e_commerce.website.dto.RegistrationDto;
import spring.projects.e_commerce.website.service.CustomerService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationDto registrationDto) {
            customerService.registerUser(registrationDto);
            return ResponseEntity.ok().body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
            String jwt = customerService.loginUser(loginDto);
            return ResponseEntity.ok().body(jwt);
    }
}
