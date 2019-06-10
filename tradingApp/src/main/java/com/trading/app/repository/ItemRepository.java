package com.trading.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trading.app.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	public Item findByItemId(Integer itemId);
}
