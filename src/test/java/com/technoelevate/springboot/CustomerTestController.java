package com.technoelevate.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technoelevate.springboot.controller.CustomerController;
import com.technoelevate.springboot.entity.Customer;
import com.technoelevate.springboot.entity.UserPasswordDTO;
import com.technoelevate.springboot.message.Message;
import com.technoelevate.springboot.service.AdminService;
import com.technoelevate.springboot.service.CustomerService;

@WebMvcTest
public class CustomerTestController {
	@MockBean
	private AdminService service;
	@MockBean
	private CustomerService customerService;
	@MockBean
	private CustomerController controller;
	@Autowired
	private MockMvc mockMvc;
	private static ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void testUserLogin() throws UnsupportedEncodingException, Exception {
		UserPasswordDTO dto = new UserPasswordDTO("Sahid", "sahid@123");
		Customer customer = new Customer(dto.getUserName(), dto.getPassword());
		Message message = new Message();
		ResponseEntity<Message> msg=new ResponseEntity<Message>(message, HttpStatus.OK);
		message.setData(customer);
		Mockito.when(controller.loginCustomer(ArgumentMatchers.anyObject(), ArgumentMatchers.anyObject()))
				.thenReturn(msg);
		String jsonObject = mapper.writeValueAsString(dto);
		String result = mockMvc
				.perform(post("/customer/login").contentType(MediaType.APPLICATION_JSON).content(jsonObject)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		Message message2 = mapper.readValue(result, Message.class);
		Map<String, String> map = (Map<String, String>) message2.getData();
		for (Map.Entry<String, String> dto1 : map.entrySet()) {
			assertEquals(dto.getUserName(), dto1.getValue());
			break;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void testDeposite() throws Exception {
		UserPasswordDTO dto = new UserPasswordDTO("Sahid", "sahid@123");
		Customer customer = new Customer(dto.getUserName(), dto.getPassword());
		Message message = new Message();
		message.setData(customer);
		ResponseEntity<Message> msg=new ResponseEntity<Message>(message, HttpStatus.OK);
		Mockito.when(controller.deposite(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyObject()))
				.thenReturn(msg);
		String result = mockMvc.perform(put("/customer/deposite/" + 1000).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		System.out.println(result);
		Message message2 = mapper.readValue(result, Message.class);
		Map<String, String> map = (Map<String, String>) message2.getData();
		for (Map.Entry<String, String> dto1 : map.entrySet()) {
			assertEquals(dto.getUserName(), dto1.getValue());
			break;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void testWidtdraw() throws Exception {
		UserPasswordDTO dto = new UserPasswordDTO("Sahid", "sahid@123");
		Customer customer = new Customer(dto.getUserName(), dto.getPassword());
		Message message = new Message();
		message.setData(customer);
		ResponseEntity<Message> msg=new ResponseEntity<Message>(message, HttpStatus.OK);
		Mockito.when(controller.withdraw(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyObject()))
				.thenReturn(msg);
		String result = mockMvc.perform(put("/customer/withdraw/" + 1000).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		Message message2 = mapper.readValue(result, Message.class);
		Map<String, String> map = (Map<String, String>) message2.getData();
		for (Map.Entry<String, String> dto1 : map.entrySet()) {
			assertEquals(dto.getUserName(), dto1.getValue());
			break;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void testGetBalance() throws Exception {
		UserPasswordDTO dto = new UserPasswordDTO("Sahid", "sahid@123");
		Customer customer = new Customer(dto.getUserName(), dto.getPassword());
		Message message1 = new Message();
		message1.setData(customer);
		ResponseEntity<Message> msg=new ResponseEntity<Message>(message1, HttpStatus.OK);
		Mockito.when(controller.getBalance(ArgumentMatchers.anyObject())).thenReturn(msg);
		String result = mockMvc.perform(get("/customer/balance").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		Message message2 = mapper.readValue(result, Message.class);
		Map<String, String> map = (Map<String, String>) message2.getData();
		for (Map.Entry<String, String> dto1 : map.entrySet()) {
			assertEquals(dto.getUserName(), dto1.getValue());
			break;
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void logoutCustomer() throws Exception {
		UserPasswordDTO dto = new UserPasswordDTO("Sahid", "sahid@123");
		Customer customer = new Customer(dto.getUserName(), dto.getPassword());
		Message message1 = new Message();
		message1.setData(customer);
		ResponseEntity<Message> msg=new ResponseEntity<Message>(message1, HttpStatus.OK);
		Mockito.when(controller.logoutCustomer(ArgumentMatchers.anyObject(), ArgumentMatchers.anyObject()))
				.thenReturn(msg);
		String jsonObject = mapper.writeValueAsString(dto);
		String result = mockMvc
				.perform(get("/customer/logout").contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		Message message2 = mapper.readValue(result, Message.class);
		Map<String, String> map = (Map<String, String>) message2.getData();
		for (Map.Entry<String, String> dto1 : map.entrySet()) {
			assertEquals(dto.getUserName(), dto1.getValue());
			break;
		}
	}
}
