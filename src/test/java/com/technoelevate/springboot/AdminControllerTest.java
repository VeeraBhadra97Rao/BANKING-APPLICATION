package com.technoelevate.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technoelevate.springboot.entity.Admin;
import com.technoelevate.springboot.message.Message;
import com.technoelevate.springboot.service.AdminService;
import com.technoelevate.springboot.service.CustomerService;

@WebMvcTest
public class AdminControllerTest {

	@MockBean
	private AdminService service;
	@MockBean
	private CustomerService customerService;
	@Autowired
	private MockMvc mockMvc;
	private static ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings({ "unchecked"})
	@Test
	public void loginTest() throws Exception {
		Admin admin = new Admin("Rakesh", "rakesh@123");
		Message message = new Message();
		message.setData(admin);
		Mockito.when(service.findByUserName(Mockito.any(), Mockito.any())).thenReturn(message);
		String jsonObject = mapper.writeValueAsString(admin);
		System.out.println(jsonObject);
		String result = mockMvc
				.perform(post("/admin/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonObject)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		System.out.println(result);
		Message c = mapper.readValue(result, Message.class);
		Map<String, String> map = (Map<String, String>) c.getData();
		for (Map.Entry<String, String> m : map.entrySet()) {
			assertEquals(admin.getUserName(), m.getValue());
			break;
		}

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testReadAll() throws Exception {
		Admin admin = new Admin(100, "Rakesh", "rakesh@123");
		Message message = new Message();
		message.setData(admin);
		Mockito.when(service.getAllCustomer(Mockito.any())).thenReturn(message);
		String jsonObject = mapper.writeValueAsString(admin);
		String result = mockMvc
				.perform(get("/admin/customers").sessionAttr("admin", admin).contentType(MediaType.APPLICATION_JSON)
						.content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		Message c = mapper.readValue(result, Message.class);
		Map<String, String> map = (Map<String, String>) c.getData();
		for (Map.Entry<String, String> m : map.entrySet()) {
			assertEquals(admin.getUserName(), m.getValue());
			break;
		}
	}

	@SuppressWarnings({ "unchecked"})
	@Test
	public void logoutAdmin() throws Exception {
		Admin admin = new Admin("Rakesh", "rakesh@123");
		Message message = new Message();
		message.setData(admin);
		String jsonObject = mapper.writeValueAsString(admin);
		String result = mockMvc
				.perform(get("/admin/logout").sessionAttr("admin", admin).contentType(MediaType.APPLICATION_JSON).content(jsonObject))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
		Message c = mapper.readValue(result, Message.class);
		Map<String, String> map = (Map<String, String>) c.getData();
		for (Map.Entry<String, String> m : map.entrySet()) {
			assertEquals(admin.getUserName(), m.getValue());
			break;
		}
	}
}
