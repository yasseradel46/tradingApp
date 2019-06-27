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
public class ReportedUserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String notes;

	private LookupDTO reportReson;

	private UserProfileDTO reportedUser;

	private UserProfileDTO reporterUser;

}