package com.supermarket.repository;

import com.supermarket.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

@Repository
public interface StatusRepo extends JpaRepository<Status,Long> {
    Status findById(short id);
    List<Status> findAll();
    void deleteById(short id);
}
