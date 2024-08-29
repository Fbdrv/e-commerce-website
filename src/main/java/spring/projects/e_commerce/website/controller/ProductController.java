package spring.projects.e_commerce.website.controller;

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
import spring.projects.e_commerce.website.dto.ProductUpdateDto;
import spring.projects.e_commerce.website.enums.RoleEnum;
import spring.projects.e_commerce.website.service.ProductService;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<String> getAll() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@AuthenticationPrincipal CustomerDto customerDto,
                                             @RequestBody ProductDto productDto) {
        if (customerDto.getRoleEnum().equals(RoleEnum.SUPER_ADMIN)) {
            return productService.addProduct(productDto);
        } else {
            return ResponseEntity.badRequest().body("Only admins can add products.");
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@AuthenticationPrincipal CustomerDto customerDto
            , @PathVariable Long productId) {
        if (customerDto.getRoleEnum().equals(RoleEnum.SUPER_ADMIN)) {
            return productService.deleteProduct(productId);
        } else {
            return ResponseEntity.badRequest().body("Only admins can delete products.");
        }
    }

    @PutMapping("/edit/{productId}")
    public ResponseEntity<String> editProduct(@AuthenticationPrincipal CustomerDto customerDto
            , @PathVariable Long productId, @RequestBody ProductUpdateDto productUpdateDto) {
        if (customerDto.getRoleEnum().equals(RoleEnum.SUPER_ADMIN)) {
            return productService.updateProduct(productId, productUpdateDto);
        } else {
            return ResponseEntity.badRequest().body("Only admins can update products.");
        }
    }
}
