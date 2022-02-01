package com.technoelevate.springboot.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.technoelevate.springboot.entity.BalanceDetails;
import com.technoelevate.springboot.entity.Customer;
import com.technoelevate.springboot.exception.CustomerException;
import com.technoelevate.springboot.message.Message;
import com.technoelevate.springboot.repository.BalanceDetailsRepo;
import com.technoelevate.springboot.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository repository;
	@Autowired
	private Environment env;
	@Autowired
	private BalanceDetailsRepo balanceRepo;
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	@Override
	public Message findByUserName(String userName, String password) {
		Customer customer = repository.findByUserName(userName);
		if (customer != null) {
			if (customer.getPassword().equals(password)) {
				LOGGER.info("Successfully Logged in " + userName);
				return new Message(false, "Successfully Logged in " + userName, customer);
			}
			LOGGER.error("Please Enter your Correct Password");
			throw new CustomerException("Please Enter your Correct Password");
		}
		LOGGER.error("Please Enter your Correct User Name");
		throw new CustomerException("Please Enter your Correct User Name");
	}

	@Override
	public Message deposite(double amount, Customer customer) {
		double deposite = (double) Math
				.round(amount * (1 - Double.parseDouble(env.getProperty("deposite.tax"))) * 100.0) / 100.0;
		if (amount % 100 == 0 && amount > 0) {
			customer.setBalance(customer.getBalance() + deposite);
			repository.save(customer);
			balanceRepo.save(new BalanceDetails(amount, 0, new Date(), customer.getBalance() + deposite, customer));
			return new Message(false, amount + "Amount Successfully Deposited  ", customer);
		}
		LOGGER.error("The Amount Should be Multiple of 100");
		throw new CustomerException("The Amount Should be Multiple of 100");
	}

	@Override
	public Message withdraw(double amount, Customer customer) {
		double withdrawAmount = (double) Math
				.round(amount * (1 + Double.parseDouble(env.getProperty("withdraw.tax"))) * 100.0) / 100.0;
		if (amount % 100 != 0) {
			LOGGER.error("The Amount Should be Multiple of 100");
			throw new CustomerException("The Amount Should be Multiple of 100");
		}
		if (customer.getBalance() > withdrawAmount && customer.getBalance() > 500) {
			if (customer.getCount() < 3) {
				customer.setBalance(customer.getBalance() - withdrawAmount);
				customer.setCount(customer.getCount() + 1);
				repository.save(customer);
				balanceRepo.save(
						new BalanceDetails(0, amount, new Date(), customer.getBalance(), customer));
				return new Message(false, amount + " Amount Successfully Withdrawn ", customer);
			}
			LOGGER.error(" Only 3 times Can be withdrawn in a month!!!");
			throw new CustomerException(" Only 3 times Can be withdrawn in a month!!!");
		}
		LOGGER.error("Insufficient Balance!!!");
		throw new CustomerException("Insufficient Balance!!!");
	}

	@Override
	public Message getBalance(Customer customer) {
		if (customer == null || customer.getUserName() == null) {
			throw new CustomerException("Please Login First!!!");
		}
		return new Message(false, "Your Balance is : " + customer.getBalance(), customer);
	}

}
