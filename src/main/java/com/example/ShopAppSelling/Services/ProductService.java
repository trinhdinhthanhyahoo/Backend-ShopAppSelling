package com.example.ShopAppSelling.Services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.example.ShopAppSelling.DTO.ProductDTO;
import com.example.ShopAppSelling.DTO.ProductImageDTO;
import com.example.ShopAppSelling.Models.Category;
import com.example.ShopAppSelling.Models.Product;
import com.example.ShopAppSelling.Models.ProductImage;
import com.example.ShopAppSelling.Repositories.CategoryRepository;
import com.example.ShopAppSelling.Repositories.ProductImageRepository;
import com.example.ShopAppSelling.Repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) {
        try {
            Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found" + productDTO.getCategoryId()));

            Product newProduct = productRepository.save(Product.builder().name(productDTO.getName())
                    .price(productDTO.getPrice()).description(productDTO.getDescription())
                    .thumbnail(productDTO.getThumbnail()).category(existingCategory).build());

            return newProduct;
        } catch (Exception e) {
            throw new RuntimeException("Error creating product: " + e.getMessage());
        }
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        try {
            return productRepository.findAll(pageRequest);
        } catch (Exception e) {
            throw new RuntimeException("Not found products" + e.getMessage());
        }
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found" + productId));

        ProductImage newProductImage = productImageRepository.save(ProductImage.builder().product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl()).build());

        return newProductImage;
    }

    @Override
    public Product getProductById(long id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found" + id));
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found" + id));

        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found" + productDTO.getCategoryId()));
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setThumbnail(productDTO.getThumbnail());
        existingProduct.setCategory(existingCategory);

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> existingProduct = productRepository.findById(id);
        existingProduct.ifPresent(productRepository::delete);
    }

}
