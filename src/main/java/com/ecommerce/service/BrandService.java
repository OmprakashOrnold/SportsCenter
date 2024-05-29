package com.ecommerce.service;

import com.ecommerce.entity.Brand;
import com.ecommerce.model.BrandResponse;

import java.util.List;

public interface BrandService {

    List<BrandResponse> getAllBrands();
}
