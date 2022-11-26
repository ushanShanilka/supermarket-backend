package com.supermarket.repository;


import com.supermarket.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

@Repository
public interface AdminRepo extends JpaRepository<Admin,Long> {
    List<Admin> findAll ();
    Admin findByEmailAndStatusId(String email, Status status);
}
