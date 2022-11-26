package com.supermarket.repository;

import com.supermarket.entity.AdminType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

@Repository
public interface AdminTypeRepo extends JpaRepository<AdminType,Long> {
    AdminType findById(Short id);
}
