package com.ecommerce.service.impl;

import com.ecommerce.entity.Brand;
import com.ecommerce.model.BrandResponse;
import com.ecommerce.repository.BrandRepository;
import com.ecommerce.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<BrandResponse> getAllBrands() {
        log.info( "Fetching all Brands!!! " );
        List<Brand> brandList = this.brandRepository.findAll();

        List<BrandResponse> brandResponses = brandList.stream().map( this::convertToBrandResponse ).collect( Collectors.toList() );
        log.info( "Fetched all Brands!!! " );
        return brandResponses;

    }

    private BrandResponse convertToBrandResponse(Brand brand) {
        return BrandResponse.builder().id( brand.getId() ).name( brand.getName() ).build();
    }
}
