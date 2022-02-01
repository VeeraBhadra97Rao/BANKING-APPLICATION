package com.technoelevate.springboot.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({ "aid" })
public class Admin implements Serializable {
	@Id
	@SequenceGenerator(name = "mySeq_Admin", initialValue = 600, allocationSize = 100)
	@GeneratedValue(generator = "mySeq_Admin")
	private int aid;
	private String userName;
	private String password;

	public Admin(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public Admin(int aid, String userName, String password) {
		super();
		this.aid = aid;
		this.userName = userName;
		this.password = password;
	}
}
