package com.product.services.impl;

import com.product.dtos.ApiResponseMessage;
import com.product.dtos.CategoryDto;
import com.product.entities.Category;
import com.product.repositories.CategoryRepo;
import com.product.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {

        categoryDto.setCategoryId(UUID.randomUUID().toString());
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category save = this.categoryRepo.save(category);
        return this.modelMapper.map(save,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new EntityNotFoundException("Category Not found"));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());

        Category category1 = categoryRepo.save(category);
        return this.modelMapper.map(category1,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new EntityNotFoundException("Category Not found"));
        this.categoryRepo.delete(category);
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto getSingleCategory(String categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new EntityNotFoundException("Category Not found"));
        return this.modelMapper.map(category,CategoryDto.class);
    }
}
