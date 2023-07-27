package com.example.SpringBootBoard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SpringBootBoard.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long>{
	// JpaRepository의 Method extends하여 사용 가능
	// findAll()		: return List<SiteUser>
	// findById(Long)	: return SiteUser
	// save(SiteUser)	: return void
	// delete(SiteUser)	: return void
	
	Optional<Users> findByuserid(String userid);
}