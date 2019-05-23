package com.trading.app.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@Table(name = "item")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "item_id")
	private int itemId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "dail_up")
	private int dailUp;

	@Column(name = "is_service")
	private int isService;

	@Column(name = "item_country")
	private String itemCountry;

	@Column(name = "item_descrption")
	private String itemDescrption;

	@Column(name = "item_location")
	private String itemLocation;

	@Column(name = "item_name")
	private String itemName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modification_date")
	private Date modificationDate;

	// bi-directional many-to-one association to Lookup
	@ManyToOne
	@JoinColumn(name = "item_quality")
	private Lookup itemQuality;

	// bi-directional many-to-one association to Lookup
	@ManyToOne
	@JoinColumn(name = "item_status")
	private Lookup itemStatus;

	// bi-directional many-to-one association to Lookup
	@ManyToOne
	@JoinColumn(name = "item_type")
	private Lookup itemType;

	// bi-directional many-to-one association to Lookup
	@ManyToOne
	@JoinColumn(name = "item_category")
	private Lookup itemCategory;

	// bi-directional many-to-one association to UserProfile
	@ManyToOne
	@JoinColumn(name = "item_owner")
	private UserProfile itemOwner;

	// bi-directional many-to-one association to ItemComment
	@OneToMany(mappedBy = "item")
	private List<ItemComment> itemComments;

	// bi-directional many-to-one association to ItemImage
	@OneToMany(mappedBy = "item")
	private List<ItemImage> itemImages;

	// bi-directional many-to-one association to ItemSwapType
	@OneToMany(mappedBy = "wishItem")
	private List<ItemSwapType> wishItemSwapTypes;

	// bi-directional many-to-one association to ItemSwapType
	@OneToMany(mappedBy = "item")
	private List<ItemSwapType> itemSwapTypes;

	// bi-directional many-to-one association to OfferItem
	@OneToMany(mappedBy = "item")
	private List<OfferItem> offerItems;

	// bi-directional many-to-one association to UserOffer
	@OneToMany(mappedBy = "item")
	private List<UserOffer> offers;

	// bi-directional many-to-one association to UserSavedItem
	@OneToMany(mappedBy = "item")
	private List<UserSavedItem> userSavedItems;

}