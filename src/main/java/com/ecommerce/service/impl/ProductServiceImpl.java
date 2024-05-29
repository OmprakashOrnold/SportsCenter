package com.ecommerce.service.impl;

import com.ecommerce.entity.Product;
import com.ecommerce.model.ProductResponse;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse getProductById(Integer id) {
        log.info( "Fetching product by id : {}", id );
        Product product = this.productRepository.findById( id )
                .orElseThrow( () -> new RuntimeException( "Product with given Id " + id + " doesn't exist" ) );
        ProductResponse productResponse = this.convertToProductResponse( product );
        log.info( "Fetched product by id : {}", id );
        return productResponse;
    }

    @Override
    public Page<ProductResponse> getProducts(Pageable pageable) {
        log.info( "Fetching products" );
        Page<Product> productPage = this.productRepository.findAll(pageable);
        Page<ProductResponse> productResponsePage = productPage.map( this::convertToProductResponse );
        log.info( "Fetched products" );
        return productResponsePage;
    }

    @Override
    public List<ProductResponse> searchProductByName(String name) {
        log.info( "Searching  product by name {}",name );
        List<Product> products=this.productRepository.searchByName(name);
        List<ProductResponse> productResponses =products.stream().map( this::convertToProductResponse ).collect( Collectors.toList());
        return productResponses;
    }

    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder().id( product.getId() ).name( product.getName() ).description( product.getDescription() )
                .price( product.getPrice() ).pictureUrl( product.getPictureUrl() )
                .productType( product.getType().getName() ).productBrand( product.getBrand().getName() )
                .build();
    }
}
