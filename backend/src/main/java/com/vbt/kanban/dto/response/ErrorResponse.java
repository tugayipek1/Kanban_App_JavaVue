package com.vbt.kanban.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private int statusCode;
	private Date timestamp;
	private String message;

	public ErrorResponse(String message) {
		this.message = message;
	}

}