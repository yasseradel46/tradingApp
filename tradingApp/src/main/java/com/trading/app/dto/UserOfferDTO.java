package com.trading.app.dto;

import java.io.Serializable;
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
public class UserOfferDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date creationDate;

	private Date modificationDate;

	private ItemDTO item;

	private LookupDTO recieverRate;

	private LookupDTO senderRate;

	private LookupDTO offerStatus;

	private UserProfileDTO userProfile;

	private List<OfferItemDTO> offerItems;

}