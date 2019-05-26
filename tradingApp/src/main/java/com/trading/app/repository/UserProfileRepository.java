package com.trading.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.trading.app.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

	@Query("select u from UserProfile u where u.email=?1")
	public UserProfile getUserProfileByEmail(String email);

	@Modifying
	@Query("update  UserProfile u set u.isActive=1 where u.email=?1")
	public int activateUserProfile(String email);
}
