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
 * The persistent class for the user_offer database table.
 * 
 */
@Entity
@Table(name = "user_offer")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserOffer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modification_date")
	private Date modificationDate;

	// bi-directional many-to-one association to OfferItem
	@OneToMany(mappedBy = "userOffer")
	private List<OfferItem> offerItems;

	// bi-directional many-to-one association to Item
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	// bi-directional many-to-one association to Lookup
	@ManyToOne
	@JoinColumn(name = "offer_receiver_rate")
	private Lookup recieverRate;

	// bi-directional many-to-one association to Lookup
	@ManyToOne
	@JoinColumn(name = "offer_sender_rate")
	private Lookup senderRate;

	// bi-directional many-to-one association to Lookup
	@ManyToOne
	@JoinColumn(name = "offer_status")
	private Lookup offerStatus;

	// bi-directional many-to-one association to UserProfile
	@ManyToOne
	@JoinColumn(name = "from_user_id")
	private UserProfile userProfile;

}