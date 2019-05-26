package com.trading.app.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * The persistent class for the item_comment database table.
 * 
 */
@Entity
@Table(name = "item_comment")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemComment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "comment")
	private String comment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;

	// bi-directional many-to-one association to Item
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	// bi-directional many-to-one association to UserProfile
	@ManyToOne
	@JoinColumn(name = "from_user_id")
	private UserProfile userProfile;

}