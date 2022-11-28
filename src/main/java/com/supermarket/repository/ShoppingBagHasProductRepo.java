package com.supermarket.repository;

import com.supermarket.entity.Product;
import com.supermarket.entity.ShoppingBag;
import com.supermarket.entity.ShoppingBagHasProduct;
import com.supermarket.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/28/2022
 **/
public interface ShoppingBagHasProductRepo extends JpaRepository<ShoppingBagHasProduct, Long> {
    boolean existsByProductIdAndShoppingBagIdAndStatusId (Product productId, ShoppingBag shoppingBagId, Status statusId);
    ShoppingBagHasProduct findByProductIdAndShoppingBagIdAndStatusId (Product productId, ShoppingBag shoppingBagId, Status statusId);
    ShoppingBagHasProduct findByIdAndShoppingBagIdAndStatusId (Long id, ShoppingBag shoppingBagId, Status statusId);
    List<ShoppingBagHasProduct> findAllByShoppingBagIdAndStatusId (ShoppingBag shoppingBagId, Status statusId);
}
