package com.supermarket.repository;

import com.supermarket.entity.Product;
import com.supermarket.entity.ProductHasProductSize;
import com.supermarket.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/28/2022
 **/
@Repository
public interface ProductHasProductSizeRepo extends JpaRepository<ProductHasProductSize, Long> {
    ProductHasProductSize findByProductIdAndStatusId (Product productId, Status statusId);

    @Query(value = "select * from product_has_product_size phps  inner join product_size ps on phps.product_size_id = ps.id where phps.status_id=1 and phps.product_id=1", nativeQuery = true)
    ProductHasProductSize getSizesByProductId(Long productId);
}
