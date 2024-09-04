package spring.projects.e_commerce.website.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotBlank(message = "field.must.be.not.blank")
    private String name;

    @NotNull(message = "field.must.be.not.blank")
    private Double price;

    @NotBlank(message = "field.must.be.not.blank")
    private String description;
}
