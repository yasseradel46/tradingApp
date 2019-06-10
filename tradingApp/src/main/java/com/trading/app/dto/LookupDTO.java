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
public class LookupDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer lookupId;

	private Date creationDate;

	private String lookupDescription;

	private Date modificationDate;

	public LookupDTO(int lookupId) {
		super();
		this.lookupId = lookupId;
	}

}