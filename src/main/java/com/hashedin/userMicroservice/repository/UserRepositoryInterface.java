package com.hashedin.userMicroservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hashedin.userMicroservice.dao.User;

@Repository
public interface UserRepositoryInterface extends JpaRepository<User, Long>{
	
	@Query("SELECT u FROM User u WHERE u.emailId = ?1")
	Optional<User>findByEmailId(String emailId);
}
