package com.technoelevate.springboot.service;

import com.technoelevate.springboot.entity.Admin;
import com.technoelevate.springboot.message.Message;

public interface AdminService {

	Message findByUserName(String userName, String password);

	Message getAllCustomer(Admin admin);
}
