package com.supermarket.repository;

import com.supermarket.entity.AdminLoginCredential;
import com.supermarket.entity.AdminPassword;
import com.supermarket.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

@Repository
public interface AdminPasswordRepo extends JpaRepository<AdminPassword,Long> {
    AdminPassword findByAdminLoginCredentialIdAndStatusId(AdminLoginCredential adminLoginCredential, Status status);
    List<AdminPassword> findAll();
    void deleteById(Long id);
}
