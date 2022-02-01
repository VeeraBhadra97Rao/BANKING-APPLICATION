package com.technoelevate.springboot.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.technoelevate.springboot.entity.Customer;
import com.technoelevate.springboot.entity.UserPasswordDTO;
import com.technoelevate.springboot.exception.CustomerException;
import com.technoelevate.springboot.message.Message;
import com.technoelevate.springboot.service.CustomerService;

@RestController
@RequestMapping(path = "/customer", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

	@PostMapping(path = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Message> loginCustomer(@RequestBody UserPasswordDTO dto, HttpServletRequest req) {
		Message message = customerService.findByUserName(dto.getUserName(), dto.getPassword());
		HttpSession session = req.getSession();
		session.setAttribute("customer", (Customer) message.getData());
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}

	@PutMapping(path = "/withdraw/{amount}")
	public ResponseEntity<Message> withdraw(@PathVariable("amount") double amount,
			@SessionAttribute(name = "customer", required = false) Customer customer) {
		if (customer == null) {
			LOGGER.error("Please Login First!!!");
			throw new CustomerException("Please Login First!!!");
		}
		LOGGER.info(amount + "Amount Successfully Withdraw  ");
		return new ResponseEntity<Message>(customerService.withdraw(amount, customer), HttpStatus.OK);
	}

	@PutMapping(path = "/deposite/{amount}")
	public ResponseEntity<Message> deposite(@PathVariable("amount") double amount,
			@SessionAttribute(name = "customer", required = false) Customer customer) {
		if (customer == null) {
			LOGGER.error("Please Login First!!!");
			throw new CustomerException("Please Login First!!!");
		}
		LOGGER.info(amount + "Amount Successfully Deposited  ");
		return new ResponseEntity<Message>(customerService.deposite(amount, customer), HttpStatus.OK);
	}

	@GetMapping("/balance")
	public ResponseEntity<Message> getBalance(
			@SessionAttribute(name = "customer", required = false) Customer customer) {
		if (customer == null) {
			LOGGER.error("Please Login First!!!");
			throw new CustomerException("Please Login First!!!");
		}
		LOGGER.info("Your Balance is : " + customer.getBalance());
		return new ResponseEntity<Message>(customerService.getBalance(customer), HttpStatus.OK);
	}

	@GetMapping("/logout")
	public ResponseEntity<Message> logoutCustomer(HttpSession session,
			@SessionAttribute(name = "customer", required = false) Customer customer) {
		if (customer == null) {
			LOGGER.error("Please Login First!!!");
			throw new CustomerException("Please Login First!!!");
		}
		Message message = new Message(false, "Successfully Log Out " + customer.getUserName(), customer);
		LOGGER.info("Successfully Log Out " + customer.getUserName());
		session.invalidate();
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}

}
