package com.trading.app.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String comment;

	private Date creationDate;

	private UserProfileDTO userProfile;

}