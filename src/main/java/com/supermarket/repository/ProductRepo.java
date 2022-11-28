package com.supermarket.repository;

import com.supermarket.entity.Product;
import com.supermarket.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/
@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query(value = "select * from product where concat(name) like %?1% and status_id=1", nativeQuery = true)
    List<Product> searchProduct(String value);

    @Query(value = "select count(id) from product where concat(name) like %?1% and status_id=1", nativeQuery = true)
    long searchProductCount(String value);

    @Query(value = "select * from product where status_id=1 ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Product> getRandomProduct();

    Product findByIdAndStatusId (Long id, Status statusId);
}
