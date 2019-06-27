package com.trading.app.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * The persistent class for the offer_item database table.
 * 
 */
@Entity
@Table(name = "offer_item")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OfferItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "swap_value")
	private Double swapValue;

	// bi-directional many-to-one association to Item
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	// bi-directional many-to-one association to Lookup
	@ManyToOne
	@JoinColumn(name = "swap_type")
	private Lookup swapType;

	// bi-directional many-to-one association to UserOffer
	@ManyToOne
	@JoinColumn(name = "offer_id")
	private UserOffer userOffer;

}