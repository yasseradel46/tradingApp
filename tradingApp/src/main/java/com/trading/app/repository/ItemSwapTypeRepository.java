package com.trading.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.trading.app.entity.ItemSwapType;

public interface ItemSwapTypeRepository extends JpaRepository<ItemSwapType, Integer> {
	@Modifying
	@Query("delete from ItemSwapType t WHERE t.item.itemId=?1")
	public void deleteByItemId(int itemId);
}
