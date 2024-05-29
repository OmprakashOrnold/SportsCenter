package com.ecommerce.service;

import com.ecommerce.entity.Product;
import com.ecommerce.model.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductResponse getProductById(Integer id);

    Page<ProductResponse> getProducts(Pageable pageable);

    List<ProductResponse> searchProductByName(String name);

}
