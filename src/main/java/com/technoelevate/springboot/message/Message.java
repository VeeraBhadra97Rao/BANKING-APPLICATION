package com.technoelevate.springboot.message;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {
	private boolean error;
	private String message;
	private Object data;

	public Message(boolean error, String message, Object data) {
		this.error = error;
		this.message = message;
		this.data = data;
	}

	public Message(boolean error, String message, List<Object> datas) {
		this.error = error;
		this.message = message;
		for (Object object : datas) {
			this.data=object;
		}
	}
	
}
