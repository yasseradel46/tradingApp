package com.trading.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.trading.app.entity.ItemImage;

public interface ItemImageRepository extends JpaRepository<ItemImage, Integer> {
	@Modifying
	@Query("delete from ItemImage t WHERE t.item.itemId=?1")
	public void deleteByItemId(int itemId);
}
