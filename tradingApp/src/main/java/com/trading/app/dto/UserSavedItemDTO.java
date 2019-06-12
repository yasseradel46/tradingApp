package com.trading.app.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSavedItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date creationDate;

	private ItemDTO item;

}