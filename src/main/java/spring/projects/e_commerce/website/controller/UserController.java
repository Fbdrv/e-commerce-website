package spring.projects.e_commerce.website.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.projects.e_commerce.website.dto.CustomerDto;
import spring.projects.e_commerce.website.dto.CustomerUpdatingDto;
import spring.projects.e_commerce.website.service.CustomerService;
import spring.projects.e_commerce.website.service.ProductService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final CustomerService customerService;
    private final ProductService productService;

    @GetMapping("/profile")
    public CustomerDto getCurrentUser(@AuthenticationPrincipal CustomerDto customerDto) {
        return customerService.getCurrentCustomer(customerDto.getUsername());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<String> homePage(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok().body(productService.getDashboard(page).toString());
    }

    @Transactional
    @DeleteMapping("/profile/delete")
    public ResponseEntity<String> deleteCustomer(@AuthenticationPrincipal CustomerDto customerDto) {
        return customerService.deleteCustomer(customerDto.getId());
    }

    @PutMapping("/profile/edit")
    public ResponseEntity<String> updateCustomer(@AuthenticationPrincipal CustomerDto customerDto,
                                      @RequestBody CustomerUpdatingDto customerUpdatingDto) {
        return customerService.updateCustomer(customerDto.getId(), customerUpdatingDto);
    }

}
