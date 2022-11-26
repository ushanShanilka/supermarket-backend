package com.supermarket.repository;

import com.supermarket.entity.Status;
import com.supermarket.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
	boolean existsByEmailAndStatusId (String email, Status statusId);
	boolean existsByIdAndStatusId (Long id, Status statusId);
	User findByEmailAndStatusId(String email, Status status);
	User findByIdAndStatusId(Long userId, Status status);
}
