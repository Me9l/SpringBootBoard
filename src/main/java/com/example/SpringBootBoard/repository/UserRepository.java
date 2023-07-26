package com.example.SpringBootBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBootBoard.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	// JpaRepository의 Method extends하여 사용 가능
	// findAll()		: return List<SiteUser>
	// findById(Long)	: return SiteUser
	// save(SiteUser)	: return void
	// delete(SiteUser)	: return void
	
}