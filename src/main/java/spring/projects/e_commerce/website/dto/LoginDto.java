package spring.projects.e_commerce.website.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {

    @NotBlank(message = "field.must.be.not.blank")
    private String username;

    @NotBlank(message = "field.must.be.not.blank")
    private String password;
}
