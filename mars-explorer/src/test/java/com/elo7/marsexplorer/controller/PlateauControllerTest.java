package com.elo7.marsexplorer.controller;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.elo7.marsexplorer.Application;
import com.elo7.marsexplorer.domain.Plateau;
import com.elo7.marsexplorer.repository.PlateauRepository;
import com.elo7.marsexplorer.validation.ResourceValidationUtil;
import com.google.gson.Gson;

/**
 * Teste para {@link PlateauController}
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PlateauControllerTest {

	private MockMvc mockMvc;

	private Gson gson = new Gson();

	@Mock
	private PlateauRepository plateauRepository;

	@Mock
	private ResourceValidationUtil validationUtil;

	@Before
	public void setup() {
		PlateauController controller = new PlateauController();
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(controller, "plateauRepository", plateauRepository);
		ReflectionTestUtils.setField(controller, "validationUtil", validationUtil);
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void postPlateauShouldSavePlateauAndReturnSavedObject() throws Exception {
		Plateau plateau = new Plateau(3, 4);
		ReflectionTestUtils.setField(plateau, "id", 0);
		Mockito.when(plateauRepository.save(Matchers.any(Plateau.class))).thenReturn(plateau);

		String json = gson.toJson(plateau);
		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/plateaus").contentType("application/json;charset=UTF-8").content(json);
		ResultActions result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isCreated());
		result.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id", org.hamcrest.Matchers.is(0)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.x", org.hamcrest.Matchers.is(3)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.y", org.hamcrest.Matchers.is(4)));
	}

	@Test
	public void getPlateauShouldFindPlateauValidateItsExistenceAndReturnPlateau() throws Exception {
		Plateau plateau = new Plateau(3, 4);
		ReflectionTestUtils.setField(plateau, "id", 1);
		Mockito.when(plateauRepository.findOne(1)).thenReturn(plateau);

		MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/plateaus/1");
		ResultActions result = this.mockMvc.perform(get);
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id", org.hamcrest.Matchers.is(1)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.x", org.hamcrest.Matchers.is(3)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.y", org.hamcrest.Matchers.is(4)));
	}

	@Test
	public void getAllPlateausShouldFindAllPlateausAndReturnList() throws Exception {
		Plateau plateau = new Plateau(3, 4);
		ReflectionTestUtils.setField(plateau, "id", 1);
		Mockito.when(plateauRepository.findAll()).thenReturn(Arrays.asList(plateau));

		MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/plateaus");
		ResultActions result = this.mockMvc.perform(get);
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		result.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(1)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", org.hamcrest.Matchers.is(1)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$[0].x", org.hamcrest.Matchers.is(3)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$[0].y", org.hamcrest.Matchers.is(4)));
	}

	@Test
	public void postInvalidPlateuShouldFail() throws Exception {
		// garante que o @Valid está sendo aplicado
		Plateau plateau = new Plateau(0, 0);
		ReflectionTestUtils.setField(plateau, "id", 0);

		String json = gson.toJson(plateau);
		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/plateaus").contentType("application/json;charset=UTF-8").content(json);
		ResultActions result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

}
