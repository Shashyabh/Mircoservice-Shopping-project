package com.product.services.impl;

import com.product.dtos.CategoryDto;
import com.product.dtos.ProductDto;
import com.product.entities.Category;
import com.product.entities.Product;
import com.product.repositories.CategoryRepo;
import com.product.repositories.ProductRepo;
import com.product.services.CategoryService;
import com.product.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public ProductDto create(ProductDto productDto) {
        productDto.setProductId(UUID.randomUUID().toString());

        Product product = this.modelMapper.map(productDto, Product.class);
        Product product1 = this.productRepo.save(product);
        return modelMapper.map(product1,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {

        Product product = this.productRepo.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        product.setAddedDate(new Date());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setTitle(productDto.getTitle());
        Product save = productRepo.save(product);
        return this.modelMapper.map(save,ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        this.productRepo.delete(product);
    }

    @Override
    public ProductDto getSingleProduct(String productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return this.modelMapper.map(product,ProductDto.class);
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Category Not found"));
        Product product = modelMapper.map(productDto, Product.class);
        product.setAddedDate(new Date());
        product.setProductId(UUID.randomUUID().toString());
        product.setCategory(category);
        productRepo.save(product);
        return this.modelMapper.map(product,ProductDto.class);
    }

    @Override
    public List<ProductDto> getProductWithCategory(String categoryId) {
        List<Product> products = productRepo.findProductByCategoryId(categoryId);
        List<ProductDto> productDtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        return productDtos;
    }

    @Override
    public List<ProductDto> getAll(){
        List<Product> products = this.productRepo.findAll();

        List<ProductDto> productDtos = products.stream().map(product -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        return productDtos;
    }
}
