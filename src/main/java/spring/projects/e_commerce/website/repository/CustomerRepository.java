package spring.projects.e_commerce.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.projects.e_commerce.website.entity.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmailIgnoreCase(String email);
    Optional<Customer> findByUsernameIgnoreCase(String username);
    @Query("SELECT c FROM Customer c WHERE c.id = ?1")
    Optional<Customer> findById(Long id);
    void deleteCustomerById(Long id);

}
