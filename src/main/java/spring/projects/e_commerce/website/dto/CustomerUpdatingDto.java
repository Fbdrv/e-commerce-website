package spring.projects.e_commerce.website.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdatingDto {

    private String username;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
                , message = "password.must.be.at.least.8.characters.and.contain.minimum.1" +
                    ".letter.and.1.number")
    private String password;
    @Email
    private String email;
    private String firstName;
    private String lastName;


}
