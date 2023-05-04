package com.pa.productservice.controller;

import com.pa.productservice.payload.request.ProductRequest;
import com.pa.productservice.payload.response.ProductResponse;
import com.pa.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {
    @Autowired
    ProductService productService;


        @PostMapping()
        @ResponseStatus(HttpStatus.CREATED)
        public void createProduct(@RequestBody ProductRequest productRequest){
            productService.createProduct(productRequest);
        }

        @GetMapping()
        @ResponseStatus(HttpStatus.OK)
        public List<ProductResponse> getAllProducts(){
            return productService.getAllProducts();
        }
}
