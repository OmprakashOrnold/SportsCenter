package com.ecommerce.controller;

import com.ecommerce.model.BrandResponse;
import com.ecommerce.model.ProductResponse;
import com.ecommerce.model.TypeResponse;
import com.ecommerce.service.BrandService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class Controller {

    private final ProductService productService;

    private final TypeService typeService;

    private final BrandService brandService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Integer productId) {
        ProductResponse productResponse = productService.getProductById( productId );
        return new ResponseEntity<>( productResponse, HttpStatus.OK );
    }

    @GetMapping("")
    public ResponseEntity<Page<ProductResponse>> getProducts(
              @PageableDefault(size = 5) Pageable pageable,
              @RequestParam(name = "keyword", required = false) String keyword,
              @RequestParam(name = "sort", defaultValue = "name") String sort,
              @RequestParam(name = "order", defaultValue = "asc") String order){
        Page<ProductResponse> productResponsePage;
        if (keyword != null && !keyword.isEmpty()) {
            List<ProductResponse> productResponses = productService.searchProductByName( keyword );
            productResponsePage = new PageImpl<>( productResponses,pageable,productResponses.size() );
        } else {
            Sort.Direction direction ="asc".equalsIgnoreCase( order )?Sort.Direction.ASC : Sort.Direction.DESC;
            Sort sorting =Sort.by( direction,sort );
            productResponsePage = productService.getProducts( PageRequest.of( pageable.getPageNumber(), pageable.getPageSize(),sorting ) );
        }
        return new ResponseEntity<>( productResponsePage, HttpStatus.OK );
    }

    @GetMapping("/types")
    public ResponseEntity<List<TypeResponse>> getProductTypes() {
        List<TypeResponse> typeResponses = typeService.getAllTypes();
        return new ResponseEntity<>( typeResponses, HttpStatus.OK );
    }


    @GetMapping("/brands")
    public ResponseEntity<List<BrandResponse>> getBrands() {
        List<BrandResponse> brandResponses = brandService.getAllBrands();
        return new ResponseEntity<>( brandResponses, HttpStatus.OK );
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> seachProductsByName(@RequestParam("name") String name) {
        List<ProductResponse> productResponses = productService.searchProductByName( name );
        return new ResponseEntity<>( productResponses, HttpStatus.OK );
    }
}
