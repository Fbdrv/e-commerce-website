package spring.projects.e_commerce.website.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDto {

    @NotBlank(message = "field.must.be.not.blank")
    private String name;

    @NotBlank(message = "field.must.be.not.blank")
    private Double price;

    @NotBlank(message = "field.must.be.not.blank")
    private String description;
}
