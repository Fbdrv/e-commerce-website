package spring.projects.e_commerce.website.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.projects.e_commerce.website.dto.CustomerDto;
import spring.projects.e_commerce.website.enums.RoleEnum;
import spring.projects.e_commerce.website.service.CustomerService;

@RestController()
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CustomerService customerService;

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId,
                                                 @AuthenticationPrincipal CustomerDto customerDto) {
        if (customerDto.getRoleEnum().equals(RoleEnum.SUPER_ADMIN) && !customerId.equals(customerDto.getId())) {
            customerService.deleteCustomer(customerId);
            return ResponseEntity.ok().body("Customer with: " + customerId + " id was deleted");
        } else {
            return ResponseEntity.badRequest().body("Only admins can delete customers.");
        }
    }

    @DeleteMapping("/delete/allCustomers")
    public ResponseEntity<String> deleteAllCustomers(@AuthenticationPrincipal CustomerDto customerDto) {
        if (customerDto.getRoleEnum().equals(RoleEnum.SUPER_ADMIN)) {
            return customerService.deleteAllCustomers();
        } else {
            return ResponseEntity.badRequest().body("Only admins can delete customers.");
        }
    }

    @GetMapping("/allCustomers")
    public ResponseEntity<String> getAllCustomers(@AuthenticationPrincipal CustomerDto customerDto) {
        if (customerDto.getRoleEnum().equals(RoleEnum.SUPER_ADMIN)) {
            return ResponseEntity.ok().body(customerService.getAllCustomers().toString());
        } else {
            return ResponseEntity.badRequest().body("Only admins can get all customers.");
        }
    }
}
