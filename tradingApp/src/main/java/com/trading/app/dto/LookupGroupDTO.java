package com.trading.app.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LookupGroupDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;

}