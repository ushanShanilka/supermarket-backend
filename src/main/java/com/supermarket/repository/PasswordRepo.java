package com.supermarket.repository;

import com.supermarket.entity.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/

public interface PasswordRepo extends CrudRepository<UserPassword, Integer> {
	UserPassword findById(Long id);
	UserPassword findByUserLoginCredentialIdAndStatusId(UserLoginCredential userLoginCredential, Status status);
	List<UserPassword> findAll();
	void deleteById(Long id);
}
