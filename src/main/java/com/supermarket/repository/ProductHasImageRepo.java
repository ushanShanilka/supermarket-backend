package com.supermarket.repository;

import com.supermarket.entity.Product;
import com.supermarket.entity.ProductHasImage;
import com.supermarket.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/
@Repository
public interface ProductHasImageRepo extends JpaRepository<ProductHasImage, Long> {
    List<ProductHasImage> findByProductIdAndStatusId (Product productId, Status statusId);
}
