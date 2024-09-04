package spring.projects.e_commerce.website.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import spring.projects.e_commerce.website.dto.ProductDto;
import spring.projects.e_commerce.website.entity.Product;
import spring.projects.e_commerce.website.exception.ProductDoesntExist;
import spring.projects.e_commerce.website.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
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

    public void deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        } else {
            throw new ProductDoesntExist();
        }
    }

    public void updateProduct(Long productId, ProductDto productDto) {
        if (productRepository.existsById(productId)) {
            Product product = productRepository.getById(productId);

            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            productRepository.save(product);
        } else {
            throw new ProductDoesntExist();
        }
    }

    public List<Product> getDashboard(int page) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        Page<Product> productPage = productRepository.findAll(pageRequest);
        return productPage.getContent();
    }
}
