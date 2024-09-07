package com.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notes.entity.UserDtls;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {
	public UserDtls findByEmail(String email);

	boolean existsByEmail(String email);
}
