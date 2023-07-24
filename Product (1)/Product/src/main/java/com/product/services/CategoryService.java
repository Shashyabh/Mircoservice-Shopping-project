package com.product.services;

import com.product.dtos.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto);

    //Update

    CategoryDto update(CategoryDto categoryDto, String categoryId);

    //Delete
    void delete(String categoryId);

    //GetAll
    List<CategoryDto> getAll();

    //Single category
    CategoryDto getSingleCategory(String categoryId);
}
