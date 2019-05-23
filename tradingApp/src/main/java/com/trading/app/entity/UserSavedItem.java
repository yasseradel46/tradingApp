package com.trading.app.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * The persistent class for the user_saved_item database table.
 * 
 */
@Entity
@Table(name = "user_saved_item")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSavedItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;

	// bi-directional many-to-one association to Item
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	// bi-directional many-to-one association to UserProfile
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserProfile userProfile;

}