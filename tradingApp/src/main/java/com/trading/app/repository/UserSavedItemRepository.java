package com.trading.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.app.entity.UserSavedItem;

public interface UserSavedItemRepository extends JpaRepository<UserSavedItem, Integer> {

}
