package com.trading.app.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the item_swap_type database table.
 * 
 */
@Entity
@Table(name = "item_swap_type")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemSwapType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "swap_value")
	private Double swapValue;

	// bi-directional many-to-one association to Item
	@ManyToOne
	@JoinColumn(name = "wish_item_id")
	private Item wishItem;

	// bi-directional many-to-one association to Item
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	// bi-directional many-to-one association to Lookup
	@ManyToOne
	@JoinColumn(name = "swap_type")
	private Lookup swapType;
}