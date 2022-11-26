package com.supermarket.repository;

import com.supermarket.entity.Status;
import com.supermarket.entity.UserLoginCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ushan Shanilka <ushanshanilka80@gmail.com>
 * @since 11/27/2022
 **/
@Repository
public interface UserLoginCredentialRepo extends JpaRepository<UserLoginCredential, Long> {
    UserLoginCredential findByUserNameAndStatusId (String userName, Status statusId);
}
