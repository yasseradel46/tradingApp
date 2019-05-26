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
 * The persistent class for the user_profile database table.
 * 
 */
@Entity
@Table(name = "user_profile")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "email")
	private String email;

	@Column(name = "is_active")
	private int isActive;

	@Column(name = "location")
	private String location;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modification_date")
	private Date modificationDate;

	@Column(name = "password")
	private String password;

	@Column(name = "phone")
	private String phone;

	@Column(name = "phone_activation_code")
	private String phoneActivationCode;

	@Lob
	private byte[] photo;

	@Column(name = "user_name")
	private String userName;

	// bi-directional many-to-one association to FollowerUser
	@OneToMany(mappedBy = "followingUser")
	private List<FollowerUser> followingUsers;

	// bi-directional many-to-one association to FollowerUser
	@OneToMany(mappedBy = "follower")
	private List<FollowerUser> followerUsers;

	// bi-directional many-to-one association to Item
	@OneToMany(mappedBy = "itemOwner")
	private List<Item> items;

	// bi-directional many-to-one association to ItemComment
	@OneToMany(mappedBy = "userProfile")
	private List<ItemComment> itemComments;

	// bi-directional many-to-one association to ReportedUser
	@OneToMany(mappedBy = "reportedUser")
	private List<ReportedUser> reportedUsers;

	// bi-directional many-to-one association to ReportedUser
	@OneToMany(mappedBy = "reporterUser")
	private List<ReportedUser> reporterUsers;

	// bi-directional many-to-one association to UserOffer
	@OneToMany(mappedBy = "userProfile")
	private List<UserOffer> offers;

	// bi-directional many-to-one association to UserSavedItem
	@OneToMany(mappedBy = "userProfile")
	private List<UserSavedItem> userSavedItems;

}