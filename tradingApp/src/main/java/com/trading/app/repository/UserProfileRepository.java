package com.trading.app.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.trading.app.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

	public UserProfile findByEmail(String email);

	@Query("select u from UserProfile u where u.email=?1 and u.phoneActivationCode=?2")
	public UserProfile verifyUserPhoneActivation(String email, String phoneActivationCode);

	@Modifying
	@Query("update  UserProfile u set u.phone=?1 , u.modificationDate =?2 where u.email=?3")
	public void setUserPhone(String phone, Date modifiedDate, String email);

	@Modifying
	@Query("update  UserProfile u set u.userName=?1 , u.location=?2 , u.photo=?3 , u.modificationDate =?4 where u.email=?5")
	public void updateUserInfo(String userName, String location, byte[] photo, Date modifiedDate, String email);

	@Modifying
	@Query("update  UserProfile u set u.isActive=?1 , u.modificationDate =?2 where u.email=?3")
	public int activateUserProfile(int activationFlag, Date modifiedDate, String email);

	@Modifying
	@Query("update  UserProfile u set u.password=?1 , u.modificationDate =?2 where u.email=?3")
	public int updateUserPassword(String newPassword, Date modifiedDate, String email);

	@Modifying
	@Query("update  UserProfile u set u.phoneActivationCode=?1 , u.modificationDate =?2 where u.email=?3")
	public void setUserPhoneActivationCode(String activationCode, Date modifiedDate, String email);
}
