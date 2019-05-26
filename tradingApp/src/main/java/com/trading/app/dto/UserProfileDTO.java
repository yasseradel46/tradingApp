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
public class UserProfileDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int userId;

	private Date creationDate;

	private String email;

	private int isActive;

	private String location;

	private Date modificationDate;

	private String password;

	private String phone;

	private String phoneActivationCode;

	private byte[] photo;

	private String userName;

}