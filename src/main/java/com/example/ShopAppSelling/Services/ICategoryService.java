package com.example.ShopAppSelling.Services;

import java.util.List;

import com.example.ShopAppSelling.DTO.CategoriesDTO;
import com.example.ShopAppSelling.Models.Category;

public interface ICategoryService {
    Category createCategory(CategoriesDTO categoriesDTO);

    Category getCategoryById(Double id);

    List<Category> getAllCategories();

    Category updateCategory(Double categoryId, CategoriesDTO categoriesDTO);

    void deleteCategory(Double id);

}
