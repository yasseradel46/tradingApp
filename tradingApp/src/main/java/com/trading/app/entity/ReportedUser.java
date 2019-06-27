package com.trading.app.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * The persistent class for the reported_user database table.
 * 
 */
@Entity
@Table(name = "reported_user")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportedUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "notes")
	private String notes;

	// bi-directional many-to-one association to Lookup
	@ManyToOne
	@JoinColumn(name = "report_reason")
	private Lookup reportReson;

	// bi-directional many-to-one association to UserProfile
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserProfile reportedUser;

	// bi-directional many-to-one association to UserProfile
	@ManyToOne
	@JoinColumn(name = "reporter_id")
	private UserProfile reporterUser;

}