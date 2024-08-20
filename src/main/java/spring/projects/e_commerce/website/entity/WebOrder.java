package spring.projects.e_commerce.website.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebOrder {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToOne(optional = false)
    private ShippingAddress shippingAddress;

    @ManyToOne(optional = false)
    private BillingAddress billingAddress;

    @OneToOne(optional = false)
    private Cart cart;
}
