package com.supermarket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/28/2022
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingBagDTO implements SuperDTO{
    private Long productId;
    private Short quantity;
}
