package spring.projects.e_commerce.website.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationDto {

    @NotBlank(message = "field.must.be.not.blank")
    private String username;

    @JsonIgnore
    @NotBlank(message = "field.must.be.not.blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
                , message = "password.must.be.at.least.8.characters.and.contain.minimum.1" +
                    ".letter.and.1.number")
    private String password;

    @Email
    @NotBlank(message = "field.must.be.not.blank")
    private String email;

    @NotBlank(message = "field.must.be.not.blank")
    private String firstName;

    @NotBlank(message = "field.must.be.not.blank")
    private String lastName;

}
