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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.technoelevate.springboot.entity.Admin;
import com.technoelevate.springboot.entity.UserPasswordDTO;
import com.technoelevate.springboot.exception.CustomerException;
import com.technoelevate.springboot.message.Message;
import com.technoelevate.springboot.service.AdminService;

@RestController
@RequestMapping(path = "/admin", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	@PostMapping(path = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Message> loginAdmin(@RequestBody UserPasswordDTO dto, HttpServletRequest req) {
		Message message = adminService.findByUserName(dto.getUserName(), dto.getPassword());
		HttpSession session = req.getSession();
		session.setAttribute("admin", (Admin) message.getData());
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}

	@GetMapping(path = "/customers")
	public ResponseEntity<Message> getAllCustomer(@SessionAttribute(name = "admin", required = false) Admin admin) {
		System.out.println(admin);
		if (admin == null) {
			LOGGER.error("Please Login First!!!");
			throw new CustomerException("Please Login First!!!");
		}
		return new ResponseEntity<Message>(adminService.getAllCustomer(admin), HttpStatus.OK);
	}

	@GetMapping("/logout")
	public ResponseEntity<Message> logoutAdmin(HttpSession session, @SessionAttribute(name = "admin", required = false) Admin admin) {
		if (admin == null) {
			LOGGER.error("Please Login First!!!");
			throw new CustomerException("Please Login First!!!");
		}
		LOGGER.info("Successfully Log Out " + admin.getUserName());
		Message message = new Message(false, "Successfully Log Out " + admin.getUserName(), admin);
		session.invalidate();
		return new ResponseEntity<Message>(message, HttpStatus.OK);
	}
}
