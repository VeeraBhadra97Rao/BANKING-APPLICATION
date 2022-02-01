package com.technoelevate.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technoelevate.springboot.entity.Admin;
import com.technoelevate.springboot.exception.CustomerException;
import com.technoelevate.springboot.message.Message;
import com.technoelevate.springboot.repository.AdminRepository;
import com.technoelevate.springboot.repository.CustomerRepository;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private CustomerRepository repository;
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
//	@Autowired
//	private Environment env;

	@Override
	public Message findByUserName(String userName, String password) {
//		String AdminUserName=env.getProperty("admin.username");
//		String AdminPassword=env.getProperty("admin.password");
		Admin admin = adminRepository.findByUserName(userName);
		if (admin != null) {
			if (admin.getPassword().equals(password)) {
				LOGGER.info("Successfully Logged in " + userName);
				return new Message(false, "Logged in Admin", admin);
			}
			LOGGER.error("Please Enter your Correct Password");
			throw new CustomerException("Please Enter your Correct Password");
		}
		LOGGER.error("Please Enter your Correct User Name");
		throw new CustomerException("Please Enter your Correct User Name");
	}

	@Override
	public Message getAllCustomer(Admin admin) {
		if (admin == null || admin.getUserName() == null) {
			throw new CustomerException("Please Login First!!!");
		}
		LOGGER.info("Successfully Fatched  ");
		return new Message(false, "Successfully Fatched  ", repository.findAll());
	}

}
