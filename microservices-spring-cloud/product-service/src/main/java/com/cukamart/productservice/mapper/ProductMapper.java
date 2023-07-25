package com.cukamart.productservice.mapper;

import com.cukamart.productservice.dto.ProductRequest;
import com.cukamart.productservice.dto.ProductResponse;
import com.cukamart.productservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    Product convert(ProductRequest productRequest);

    // entity to API (controller layer)
    List<ProductResponse> convert(List<Product> products);
    ProductResponse convert(Product product);
}
