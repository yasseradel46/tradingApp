package com.trading.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.app.entity.Item;
import com.trading.app.entity.ItemComment;

public interface ItemCommentRepository extends JpaRepository<ItemComment, Integer> {
	public List<ItemComment> findByItem(Item item);
}
