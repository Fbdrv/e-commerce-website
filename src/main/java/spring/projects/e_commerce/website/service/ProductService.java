package spring.projects.e_commerce.website.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spring.projects.e_commerce.website.dto.ProductDto;
import spring.projects.e_commerce.website.dto.ProductUpdateDto;
import spring.projects.e_commerce.website.entity.Product;
import spring.projects.e_commerce.website.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<String> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(modelMapper.map(product, ProductDto.class));
        }
        if (products.isEmpty()) {
            return ResponseEntity.badRequest().body("No products found");
        } else {
            return ResponseEntity.ok().body(productDtos.toString());
        }
    }

    public ResponseEntity<String> addProduct(ProductDto productDto) {
        if (productDto.getName() == null || productDto.getDescription() == null
                || productDto.getPrice() <= 0) {
            return ResponseEntity.badRequest().body("Product full information is required");
        } else {
            Product product = new Product();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            productRepository.save(product);
            return ResponseEntity.ok().body("Product added successfully");
        }
    }

    public ResponseEntity<String> deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return ResponseEntity.ok().body("Product deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Product not found");
        }
    }

    public ResponseEntity<String> updateProduct(Long productId, ProductUpdateDto productUpdateDto) {
        if (productRepository.existsById(productId)) {
            Product product = productRepository.getById(productId);
            if (!productUpdateDto.getName().isEmpty()) {
                product.setName(productUpdateDto.getName());
            }
            if (!productUpdateDto.getDescription().isEmpty()) {
                product.setDescription(productUpdateDto.getDescription());
            }
            if (product.getPrice().equals(productUpdateDto.getPrice())) {
                product.setPrice(productUpdateDto.getPrice());
            }
            productRepository.save(product);
            return ResponseEntity.ok().body("Product updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Product not found");
        }
    }

    public ResponseEntity<String> getDashboard() {
        List<Product> products = productRepository.findAll();
        if (products.size() <= 5) {
            return ResponseEntity.ok().body(products.toString());
        }
        Collections.shuffle(products);
        return ResponseEntity.ok().body(products.subList(0, 5).toString());
    }
}
