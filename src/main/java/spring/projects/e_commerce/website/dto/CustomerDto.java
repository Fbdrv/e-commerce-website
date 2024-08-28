package spring.projects.e_commerce.website.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.projects.e_commerce.website.enums.RoleEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long id;
    private String firstName;
    private String username;
    private String email;
    private RoleEnum roleEnum;
}
