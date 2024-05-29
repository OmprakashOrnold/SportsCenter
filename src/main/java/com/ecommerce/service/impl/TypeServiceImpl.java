package com.ecommerce.service.impl;

import com.ecommerce.entity.Type;
import com.ecommerce.model.TypeResponse;
import com.ecommerce.repository.TypeRepository;
import com.ecommerce.service.TypeService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    @Override
    public List<TypeResponse> getAllTypes() {
        log.info( "Fetching all Types !!!" );
        List<Type> typeList = this.typeRepository.findAll();
        List<TypeResponse> typeResponses = typeList.stream().map( this::convertToTypeResonse ).collect( Collectors.toList() );
        log.info( "Fetched all Types !!!" );
        return typeResponses;
    }

    private TypeResponse convertToTypeResonse(Type type){
        return TypeResponse.builder().id( type.getId()).name( type.getName() ).build();
    }
}
