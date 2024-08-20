package spring.projects.e_commerce.website.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false, length = 1000)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private List<ShippingAddress> shippingAddresses;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.REMOVE,
        optional = false, orphanRemoval = true)
    private Cart cart;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.REMOVE,
        optional = false, orphanRemoval = true)
    private BillingAddress billingAddress;

}
