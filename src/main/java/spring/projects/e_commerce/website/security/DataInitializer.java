package spring.projects.e_commerce.website.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import spring.projects.e_commerce.website.entity.Customer;
import spring.projects.e_commerce.website.enums.RoleEnum;
import spring.projects.e_commerce.website.repository.CustomerRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        if (customerRepository.findByUsernameIgnoreCase("superAdmin").isEmpty()) {
            Customer superAdmin = new Customer();
            superAdmin.setUsername("superAdmin");
            superAdmin.setEmail("super@admin.com");
            superAdmin.setPassword(encoder.encode("1234"));
            superAdmin.setFirstName("Super");
            superAdmin.setLastName("Admin");
            superAdmin.setRole(RoleEnum.SUPER_ADMIN);
            customerRepository.save(superAdmin);
        }
    }
}
