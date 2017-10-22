package com.pilaf.cs.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pilaf.cs.users.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
