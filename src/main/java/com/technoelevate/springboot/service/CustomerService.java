package com.technoelevate.springboot.service;

import com.technoelevate.springboot.entity.Customer;
import com.technoelevate.springboot.message.Message;

public interface CustomerService {

	Message findByUserName(String userName,String password);

	Message deposite(double amount,Customer customer);

	Message withdraw(double amount, Customer customer);

	Message getBalance(Customer customer);
}
