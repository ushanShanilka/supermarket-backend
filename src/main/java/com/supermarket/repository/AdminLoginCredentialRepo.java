package com.supermarket.repository;

import com.supermarket.entity.Admin;
import com.supermarket.entity.AdminLoginCredential;
import com.supermarket.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

@Repository
public interface AdminLoginCredentialRepo extends JpaRepository<AdminLoginCredential, Long> {
	Optional<AdminLoginCredential> findById(Long id);
	List<AdminLoginCredential> findAll();
	void deleteById(Long id);
	AdminLoginCredential findByUserNameAndStatusId(String userName, Status status);
	AdminLoginCredential findByAdminIdAndStatusId(Admin admin, Status status);
}
