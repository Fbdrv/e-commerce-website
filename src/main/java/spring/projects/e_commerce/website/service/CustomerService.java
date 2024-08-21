package spring.projects.e_commerce.website.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import spring.projects.e_commerce.website.dto.CustomerDto;
import spring.projects.e_commerce.website.dto.LoginDto;
import spring.projects.e_commerce.website.dto.RegistrationDto;
import spring.projects.e_commerce.website.entity.Customer;
import spring.projects.e_commerce.website.exception.EmailIsTaken;
import spring.projects.e_commerce.website.exception.UsernameIsTaken;
import spring.projects.e_commerce.website.exception.WrongLoginDetails;
import spring.projects.e_commerce.website.repository.CustomerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final JWTService jwtService;
    private final ModelMapper modelMapper;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void registerUser(@RequestBody RegistrationDto customerDto) {
        if (customerRepository.findByEmailIgnoreCase(customerDto.getEmail()).isPresent()) {
        throw new EmailIsTaken();
        } else if (customerRepository.findByUsernameIgnoreCase(customerDto.getUsername()).isPresent()) {
        throw new UsernameIsTaken();
        } else {
            Customer customer = new Customer();
            customer.setUsername(customerDto.getUsername());
            customer.setEmail(customerDto.getEmail());
            customer.setPassword(encoder.encode(customerDto.getPassword()));
            customer.setFirstName(customerDto.getFirstName());
            customer.setLastName(customerDto.getLastName());

            customerRepository.save(customer);
        }
    }

    public String loginUser(@RequestBody LoginDto loginDto) {
        Optional<Customer> customer = customerRepository.findByUsernameIgnoreCase(loginDto.getUsername());
        String password = loginDto.getPassword();
        if (customer.isPresent()) {
            String encodedPassword = customer.get().getPassword();
            if (encoder.matches(password, encodedPassword)) {
                return jwtService.generateToken(customer.get());
            }
        }
        throw new WrongLoginDetails();
    }

    public CustomerDto entityToDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }
}
