package com.trading.app.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * The persistent class for the lookup database table.
 * 
 */
@Entity
@Table(name = "lookup")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Lookup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lookup_id")
	private int lookupId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "lookup_description")
	private String lookupDescription;

	@ManyToOne
	@JoinColumn(name = "lookup_group")
	private LookupGroup lookupGroup;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modification_date")
	private Date modificationDate;

	@Column(name = "parent_lookup")
	private Lookup parentLookup;

}