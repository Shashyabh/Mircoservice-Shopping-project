package com.product.services;

import com.product.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto create(ProductDto productDto);
    ProductDto update(ProductDto productDto, String productId);
    void delete(String productId);
    ProductDto getSingleProduct(String productId);

    List<ProductDto>getAll();

//    List<ProductDto> searchByTitle(String subtitle,int pageNumber,int pageSize, String sortBy, String sortDir);

    //Create product with category Id
    ProductDto createProductWithCategory(ProductDto productDto,String categoryId);

    List<ProductDto> getProductWithCategory(String categoryId);

}
