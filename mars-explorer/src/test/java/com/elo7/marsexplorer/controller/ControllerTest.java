package com.elo7.marsexplorer.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.elo7.marsexplorer.Application;
import com.elo7.marsexplorer.probe.Plateau;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ControllerTest {

	private MockMvc mockMvc;

	private Gson gson = new Gson();

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new Controller()).build();
	}

	@Test
	public void testPost() throws Exception {
		Plateau plateau = new Plateau(3, 4);
		String json = gson.toJson(plateau);
		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/plateau").contentType("application/json;charset=UTF-8").content(json);
		ResultActions result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isCreated());
		result.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.x", Matchers.is(3)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.y", Matchers.is(4)));
	}

}
