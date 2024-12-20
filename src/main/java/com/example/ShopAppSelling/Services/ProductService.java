package com.example.ShopAppSelling.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.ShopAppSelling.DTO.ProductDTO;
import com.example.ShopAppSelling.DTO.ProductImageDTO;
import com.example.ShopAppSelling.Models.Category;
import com.example.ShopAppSelling.Models.Product;
import com.example.ShopAppSelling.Models.ProductImage;
import com.example.ShopAppSelling.Repositories.CategoryRepository;
import com.example.ShopAppSelling.Repositories.ProductImageRepository;
import com.example.ShopAppSelling.Repositories.ProductRepository;
import com.example.ShopAppSelling.Responses.ProductResponse;
import com.example.ShopAppSelling.exceptions.DataNotFoundException;
import com.example.ShopAppSelling.exceptions.InvalidParamException;

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

            Product newProduct = Product.builder().name(productDTO.getName())
                    .price(productDTO.getPrice()).description(productDTO.getDescription())
                    .thumbnail(productDTO.getThumbnail()).category(existingCategory).build();

            return productRepository.save(newProduct);
        } catch (Exception e) {
            throw new RuntimeException("Error creating product: " + e.getMessage());
        }
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductResponse::fromProduct);

    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception {
        Product existingProduct = productRepository
                .findById(productId)
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + productImageDTO.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        // Ko cho insert quá 5 ảnh cho 1 sản phẩm
        List<ProductImage> images = (List<ProductImage>) productImageRepository.findByProductId(productId);
        int size = images.size();
        if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException(
                    "Number of images must be <= "
                            + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }

    @Override
    public Product getProductById(Long id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product not found" + id));
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws Exception {
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
    public void deleteProduct(Long id) {
        Optional<Product> existingProduct = productRepository.findById(id);
        existingProduct.ifPresent(productRepository::delete);
    }

}
