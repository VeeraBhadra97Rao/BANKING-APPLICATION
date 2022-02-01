package com.technoelevate.springboot.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordDTO implements Serializable {
	private String userName;
	private String password;
}
