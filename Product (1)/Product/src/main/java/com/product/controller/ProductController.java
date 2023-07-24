package com.product.controller;

import com.product.dtos.ApiResponseMessage;
import com.product.dtos.ProductDto;
import com.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductDto productDto1 = productService.create(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable String productId){
        ProductDto productDto1 = productService.update(productDto,productId);
        return new ResponseEntity<>(productDto1, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId){
        productService.delete(productId);
        ApiResponseMessage apiResponse = ApiResponseMessage.builder().message("Product is deleted successfully").status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/getSingle/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId){
        ProductDto productDto1 = productService.getSingleProduct(productId);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDto>> getAll()
    {
        List<ProductDto> pageableResponse = productService.getAll();
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    @PostMapping("/createWithCat/{categoryId}")
    public ResponseEntity<ProductDto> createWithCat(@RequestBody ProductDto productDto,@PathVariable String categoryId){
        ProductDto productDto1 = this.productService.createProductWithCategory(productDto, categoryId);
        return new ResponseEntity<>(productDto1,HttpStatus.OK);
    }

    @GetMapping("/getByCat/{categoryId}")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategory(@PathVariable String categoryId){
        List<ProductDto> productDtos = this.productService.getProductWithCategory(categoryId);
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }

}
