package com.elo7.marsexplorer.controller;

import static com.elo7.marsexplorer.domain.NavigationCommand.L;
import static com.elo7.marsexplorer.domain.NavigationCommand.M;
import static com.elo7.marsexplorer.domain.NavigationCommand.R;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.elo7.marsexplorer.Application;
import com.elo7.marsexplorer.domain.CardinalDirection;
import com.elo7.marsexplorer.domain.Plateau;
import com.elo7.marsexplorer.dto.CommandSequenceDTO;
import com.elo7.marsexplorer.dto.ProbeDTO;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.google.gson.Gson;

/**
 * Testes integrados para {@link PlateauController}
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DirtiesContext
@DatabaseSetup("/dbunit/dataset.xml")
public class AcceptanceTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private Gson gson = new Gson();

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void acceptanceTest() throws Exception {
		// Criando o plateau
		Plateau plateau = new Plateau(5, 5);
		String gsonPlateu = gson.toJson(plateau);
		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/plateaus").contentType("application/json;charset=UTF-8").content(gsonPlateu);
		ResultActions result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isCreated());

		plateau = gson.fromJson(result.andReturn().getResponse().getContentAsString(), Plateau.class);

		// criando a sonda 1
		ProbeDTO probe1 = new ProbeDTO();
		probe1.setX(1);
		probe1.setY(2);
		probe1.setDirection(CardinalDirection.N);
		String gsonProbe = gson.toJson(probe1);
		post = MockMvcRequestBuilders.post("/plateaus/" + plateau.getId() + "/probes").contentType("application/json;charset=UTF-8").content(gsonProbe);
		result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isCreated());
		probe1 = gson.fromJson(result.andReturn().getResponse().getContentAsString(), ProbeDTO.class);

		// enviando commandos para sonda 1
		CommandSequenceDTO commands = new CommandSequenceDTO();
		commands.setCommands(Arrays.asList(L, M, L, M, L, M, L, M, M));
		String gsonCommands = gson.toJson(commands);
		post = MockMvcRequestBuilders.post("/plateaus/" + plateau.getId() + "/probes/" + probe1.getId() + "/command-sequence").contentType("application/json;charset=UTF-8")
				.content(gsonCommands);
		result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isOk());

		// criando a sonda 2
		ProbeDTO probe2 = new ProbeDTO();
		probe2.setX(3);
		probe2.setY(3);
		probe2.setDirection(CardinalDirection.E);
		gsonProbe = gson.toJson(probe2);
		post = MockMvcRequestBuilders.post("/plateaus/" + plateau.getId() + "/probes").contentType("application/json;charset=UTF-8").content(gsonProbe);
		result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isCreated());
		probe2 = gson.fromJson(result.andReturn().getResponse().getContentAsString(), ProbeDTO.class);

		// enviando commandos para sonda 2
		commands = new CommandSequenceDTO();
		commands.setCommands(Arrays.asList(M, M, R, M, M, R, M, R, R, M));
		gsonCommands = gson.toJson(commands);
		post = MockMvcRequestBuilders.post("/plateaus/" + plateau.getId() + "/probes/" + probe2.getId() + "/command-sequence").contentType("application/json;charset=UTF-8")
				.content(gsonCommands);
		result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isOk());

		MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/plateaus/" + plateau.getId() + "/probes/" + probe1.getId());
		result = this.mockMvc.perform(get);
		result.andExpect(MockMvcResultMatchers.status().isOk());
		ProbeDTO movedProbe1 = gson.fromJson(result.andReturn().getResponse().getContentAsString(), ProbeDTO.class);
		Assert.assertEquals("1 3 N", movedProbe1.getX() + " " + movedProbe1.getY() + " " + movedProbe1.getDirection());

		get = MockMvcRequestBuilders.get("/plateaus/" + plateau.getId() + "/probes/" + probe2.getId());
		result = this.mockMvc.perform(get);
		result.andExpect(MockMvcResultMatchers.status().isOk());
		ProbeDTO movedProbe2 = gson.fromJson(result.andReturn().getResponse().getContentAsString(), ProbeDTO.class);
		Assert.assertEquals("5 1 E", movedProbe2.getX() + " " + movedProbe2.getY() + " " + movedProbe2.getDirection());
	}

}
