package com.trading.app.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemSwapTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private double swapValue;

	private ItemDTO wishItem;

	private ItemDTO item;

	private LookupDTO swapType;

	public ItemSwapTypeDTO(double swapValue, LookupDTO swapType) {
		super();
		this.swapValue = swapValue;
		this.swapType = swapType;
	}

	public ItemSwapTypeDTO(LookupDTO swapType) {
		super();
		this.swapType = swapType;
	}

	public ItemSwapTypeDTO(ItemDTO wishItem, LookupDTO swapType) {
		super();
		this.wishItem = wishItem;
		this.swapType = swapType;
	}

}