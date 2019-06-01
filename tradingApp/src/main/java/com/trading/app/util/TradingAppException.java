package com.trading.app.util;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TradingAppException {
	private Date timestamp;
	private int status;
	private String error;
	private String message;

	public TradingAppException(Date timestamp, int status, String message) {
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
	}
}
