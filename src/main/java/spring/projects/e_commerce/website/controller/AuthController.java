package spring.projects.e_commerce.website.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.projects.e_commerce.website.dto.CustomerDto;
import spring.projects.e_commerce.website.dto.LoginDto;
import spring.projects.e_commerce.website.dto.RegistrationDto;
import spring.projects.e_commerce.website.repository.CustomerRepository;
import spring.projects.e_commerce.website.service.CustomerService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationDto customerDto) {
        try {
            customerService.registerUser(customerDto);
            return ResponseEntity.ok().body("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            String jwt = customerService.loginUser(loginDto);
            return ResponseEntity.ok().body(jwt);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public CustomerDto getCurrentUser(@AuthenticationPrincipal CustomerDto customerDto) {
        return customerDto;
    }
}
