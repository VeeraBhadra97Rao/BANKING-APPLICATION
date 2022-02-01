package com.technoelevate.springboot.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({ "cid", "password", "balance", "count" })
@Table(name = "customer_details", uniqueConstraints = @UniqueConstraint(columnNames = { "userName", "accountNo" }))
public class Customer implements Serializable {
	@Id
	@SequenceGenerator(name = "mySeq_Customer", initialValue = 600, allocationSize = 100)
	@GeneratedValue(generator = "mySeq_Customer")
	private int cid;
	private String userName;
	private long accountNo;
	private String password;
	private double balance;
	private int count;
	@OneToMany( mappedBy = "customer")
	private List<BalanceDetails> balanceDetails;
	public Customer(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	

}
