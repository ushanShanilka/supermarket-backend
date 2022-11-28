package com.supermarket.service;

import com.supermarket.dto.ShoppingBagDTO;

import java.util.List;
import java.util.Map;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/28/2022
 **/
public interface ShoppingBagService {
    String saveShoppingBag(String AUTHORIZATION_HEADER, ShoppingBagDTO dto);
    List<Map<String, Object>> getAllShoppingBagItems(String AUTHORIZATION_HEADER);
    String removeProductFromShoppingBag(String AUTHORIZATION_HEADER, Long id);
}
