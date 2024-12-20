package com.example.ShopAppSelling.Services;

import java.util.List;

import com.example.ShopAppSelling.DTO.CategoriesDTO;
import com.example.ShopAppSelling.Models.Category;

public interface ICategoryService {
    Category createCategory(CategoriesDTO categoriesDTO);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category updateCategory(Long categoryId, CategoriesDTO categoriesDTO);

    void deleteCategory(Long id);

}
