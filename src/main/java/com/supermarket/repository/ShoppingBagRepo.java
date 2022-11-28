package com.supermarket.repository;

import com.supermarket.entity.ShoppingBag;
import com.supermarket.entity.ShoppingBagStatus;
import com.supermarket.entity.Status;
import com.supermarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/28/2022
 **/
@Repository
public interface ShoppingBagRepo extends JpaRepository<ShoppingBag, Long> {
    boolean existsByUserIdAndShoppingBagStatusIdAndStatusId (User userId, ShoppingBagStatus shoppingBagStatusId, Status statusId);
    ShoppingBag findByUserIdAndShoppingBagStatusIdAndStatusId (User userId, ShoppingBagStatus shoppingBagStatusId, Status statusId);
}
