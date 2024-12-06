package com.example.ShopAppSelling.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ShopAppSelling.DTO.CategoriesDTO;
import com.example.ShopAppSelling.Models.Category;
import com.example.ShopAppSelling.Repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoriesDTO categoriesDTO) {
        Category newCategory = Category.builder()
                .name(categoriesDTO.getName())
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long categoryId, CategoriesDTO categoriesDTO) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoriesDTO.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

}
