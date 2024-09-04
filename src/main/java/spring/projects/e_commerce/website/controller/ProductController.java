package spring.projects.e_commerce.website.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.projects.e_commerce.website.dto.CustomerDto;
import spring.projects.e_commerce.website.dto.ProductDto;
import spring.projects.e_commerce.website.enums.RoleEnum;
import spring.projects.e_commerce.website.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@AuthenticationPrincipal CustomerDto customerDto,
                                             @Valid @RequestBody ProductDto productDto) {
        if (customerDto.getRoleEnum().equals(RoleEnum.SUPER_ADMIN)) {
            productService.addProduct(productDto);
            return ResponseEntity.ok().body("Product added");
        } else {
            return ResponseEntity.badRequest().body("Only admins can add products.");
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@AuthenticationPrincipal CustomerDto customerDto
            , @PathVariable Long productId) {
        if (customerDto.getRoleEnum().equals(RoleEnum.SUPER_ADMIN)) {
            productService.deleteProduct(productId);
            return ResponseEntity.ok().body("Product deleted");
        } else {
            return ResponseEntity.badRequest().body("Only admins can delete products.");
        }
    }

    @PutMapping("/edit/{productId}")
    public ResponseEntity<String> editProduct(@AuthenticationPrincipal CustomerDto customerDto
            , @PathVariable Long productId, @Valid @RequestBody ProductDto productDto) {
        if (customerDto.getRoleEnum().equals(RoleEnum.SUPER_ADMIN)) {
            productService.updateProduct(productId, productDto);
            return ResponseEntity.ok().body("Product updated");
        } else {
            return ResponseEntity.badRequest().body("Only admins can update products.");
        }
    }
}
