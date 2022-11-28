package com.supermarket.repository;

import com.supermarket.entity.ShoppingBagStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

@Repository
public interface ShoppingBagStatusRepo extends JpaRepository<ShoppingBagStatus,Long> {
    ShoppingBagStatus findById(short id);
    List<ShoppingBagStatus> findAll();
    void deleteById(short id);
}
