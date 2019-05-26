package com.trading.app.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the lookup_parent database table.
 * 
 */
@Entity
@Table(name = "lookup_group")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class LookupGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "name")
	private String name;

	@OneToMany
	@JoinColumn(name = "lookup_id")
	private List<Lookup> lookups;

}