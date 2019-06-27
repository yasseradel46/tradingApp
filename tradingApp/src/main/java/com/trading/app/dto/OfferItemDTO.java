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
public class OfferItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Double swapValue;

	private ItemDTO item;

	private LookupDTO swapType;

	public OfferItemDTO(ItemDTO item, LookupDTO swapType) {
		super();
		this.item = item;
		this.swapType = swapType;
	}

	public OfferItemDTO(Double swapValue, LookupDTO swapType) {
		super();
		this.swapValue = swapValue;
		this.swapType = swapType;
	}
}