package spring.projects.e_commerce.website.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import spring.projects.e_commerce.website.dto.ProductDto;
import spring.projects.e_commerce.website.entity.Product;
import spring.projects.e_commerce.website.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> findAll() {
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            productDtoList.add(modelMapper.map(product, ProductDto.class));
        }
        return productDtoList;
    }

    public void addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
    }

    public ProductDto entityToDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public void updateProduct(Long productId, String description, Double price) {
        Product product = productRepository.findById(productId).get();
        product.setDescription(description);
        product.setPrice(price);
    }
}
