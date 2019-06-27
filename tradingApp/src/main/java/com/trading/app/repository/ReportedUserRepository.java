package com.trading.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.app.entity.ReportedUser;

public interface ReportedUserRepository extends JpaRepository<ReportedUser, Integer> {

}
