package com.trading.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.app.entity.FollowerUser;
import com.trading.app.entity.UserProfile;

public interface FollowerUserRepository extends JpaRepository<FollowerUser, Integer> {
	public List<FollowerUser> findByfollowingUser(UserProfile followingUser);

	public List<FollowerUser> findByFollower(UserProfile follower);

	public FollowerUser findByFollowerAndFollowingUser(UserProfile follower, UserProfile followingUser);

}
