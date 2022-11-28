package com.supermarket.service;

import com.supermarket.dto.Paginated.PaginatedDTO;

import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/
public interface ProductService {
    PaginatedDTO searchProduct(String value);
    PaginatedDTO getRandomProducts();
    Map<String, Object> getProduct(Long id);
}
