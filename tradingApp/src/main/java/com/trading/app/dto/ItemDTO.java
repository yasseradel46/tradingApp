package com.trading.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

	private Integer itemId;

	private Date creationDate;

	private int dailUp;

	private int isService;

	private String itemCountry;

	private String itemDescrption;

	private String itemLocation;

	private String itemName;

	private Date modificationDate;

	private LookupDTO itemQuality;

	private LookupDTO itemStatus;

	private LookupDTO itemType;

	private LookupDTO itemCategory;

	private UserProfileDTO itemOwner;

	private List<ItemSwapTypeDTO> itemSwapTypes;

	private List<ItemImageDTO> itemImages;

	private List<CommentDTO> itemComments;

}