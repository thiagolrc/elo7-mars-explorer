package com.elo7.marsexplorer.controller.advice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.elo7.marsexplorer.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ControllerExceptionHandlerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void whenControllerFailWithBadRequestExceptionResponseShouldHaveMessageAndStatusCode400() throws Exception {
		MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/failWithBadRequest");
		ResultActions result = this.mockMvc.perform(get);
		result.andExpect(MockMvcResultMatchers.status().isBadRequest());
		result.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.is("teste bad request")));
	}

	@Test
	public void whenControllerFailWithResourceNotFoundExceptionResponseShouldHaveStatusCode404() throws Exception {
		MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/failWithResourceNotFound");
		ResultActions result = this.mockMvc.perform(get);
		result.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void whenControllerFailWithExceptionResponseShouldHaveStatusCode500() throws Exception {
		MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/failWithException");
		ResultActions result = this.mockMvc.perform(get);
		result.andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}

}
