package spring.projects.e_commerce.website.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.projects.e_commerce.website.dto.CustomerDto;
import spring.projects.e_commerce.website.dto.CustomerUpdatingDto;
import spring.projects.e_commerce.website.dto.LoginDto;
import spring.projects.e_commerce.website.dto.RegistrationDto;
import spring.projects.e_commerce.website.entity.Customer;
import spring.projects.e_commerce.website.exception.EmailIsTaken;
import spring.projects.e_commerce.website.exception.UsernameIsTaken;
import spring.projects.e_commerce.website.exception.WrongLoginDetails;
import spring.projects.e_commerce.website.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final JWTService jwtService;
    private final ModelMapper modelMapper;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void registerUser(RegistrationDto registrationDto) {
        if (customerRepository.findByEmailIgnoreCase(registrationDto.getEmail()).isPresent()) {
        throw new EmailIsTaken();
        } else if (customerRepository.findByUsernameIgnoreCase(registrationDto.getUsername()).isPresent()) {
        throw new UsernameIsTaken();
        } else {
            Customer customer = new Customer();
            customer.setUsername(registrationDto.getUsername());
            customer.setEmail(registrationDto.getEmail());
            customer.setPassword(encoder.encode(registrationDto.getPassword()));
            customer.setFirstName(registrationDto.getFirstName());
            customer.setLastName(registrationDto.getLastName());

            customerRepository.save(customer);
        }
    }

    public String loginUser(LoginDto loginDto) {
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

    public List<CustomerDto> getAllCustomers() {
    List<Customer> customers = customerRepository.findAll();
    List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer : customers) {
            customerDtoList.add(entityToDto(customer));
        }
    return customerDtoList;
    }
    public void deleteCustomer(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            customerRepository.deleteCustomerById(id);
        } else {
            throw new RuntimeException("User with id: " + id + " not found");
        }
    }

    public CustomerDto entityToDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    public void updateCustomer(Long customerId, CustomerUpdatingDto customerUpdatingDto) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();

            if (customerUpdatingDto.getFirstName() != null) {
                customer.setFirstName(customerUpdatingDto.getFirstName());
            }
            if (customerUpdatingDto.getLastName() != null) {
                customer.setLastName(customerUpdatingDto.getLastName());
            }

            if (customerUpdatingDto.getEmail() != null) {
                if (customerRepository.findByEmailIgnoreCase(customerUpdatingDto.getEmail()).isPresent()) {
                    throw new EmailIsTaken();
                }
                customer.setEmail(customerUpdatingDto.getEmail());
            }

            if (customerUpdatingDto.getUsername() != null) {
                if (customerRepository.findByUsernameIgnoreCase(customerUpdatingDto.getUsername()).isPresent()) {
                    throw new UsernameIsTaken();
                }
                customer.setUsername(customerUpdatingDto.getUsername());
            }
            if (customerUpdatingDto.getPassword() != null) {
                customer.setPassword(customerUpdatingDto.getPassword());
            }

            customerRepository.save(customer);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }
}
