package com.elo7.marsexplorer.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
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
import com.elo7.marsexplorer.converter.ProbeConverter;
import com.elo7.marsexplorer.domain.CardinalDirection;
import com.elo7.marsexplorer.domain.NavigationCommand;
import com.elo7.marsexplorer.domain.Plateau;
import com.elo7.marsexplorer.domain.Probe;
import com.elo7.marsexplorer.dto.CommandSequenceDTO;
import com.elo7.marsexplorer.dto.ProbeDTO;
import com.elo7.marsexplorer.repository.ProbeRepository;
import com.elo7.marsexplorer.validation.ResourceValidationUtil;
import com.google.gson.Gson;

/**
 * Teste para {@link PlateauController}
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ProbeControllerTest {

	private MockMvc mockMvc;

	private Gson gson = new Gson();

	@Mock
	private ProbeRepository probeRepository;
	@Mock
	private ResourceValidationUtil validationUtil;
	@Mock
	private ProbeConverter probeConverter;
	@Mock
	private PlateauController plateuController;
	@Captor
	private ArgumentCaptor<List<NavigationCommand>> argumentCaptor;

	@Before
	public void setup() {
		ProbeController controller = new ProbeController();
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(controller, "probeRepository", probeRepository);
		ReflectionTestUtils.setField(controller, "validationUtil", validationUtil);
		ReflectionTestUtils.setField(controller, "probeConverter", probeConverter);
		ReflectionTestUtils.setField(controller, "plateuController", plateuController);
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

	}

	@Test
	public void postProbleShouldLoadPlateauConvertDoEntitySaveAndReturnConvertedDto() throws Exception {
		Plateau plateau = new Plateau();
		Mockito.when(plateuController.getPlateau(1)).thenReturn(plateau);
		Probe probe = new Probe();
		Mockito.when(probeConverter.fromDTO(Matchers.any(ProbeDTO.class), Matchers.eq(plateau))).thenReturn(probe);
		ProbeDTO savedDto = new ProbeDTO();
		savedDto.setId(1);
		savedDto.setX(2);
		savedDto.setY(3);
		savedDto.setDirection(CardinalDirection.N);
		Mockito.when(probeRepository.save(probe)).thenReturn(probe);
		Mockito.when(probeConverter.toDTO(Matchers.eq(probe))).thenReturn(savedDto);

		ProbeDTO probeDto = new ProbeDTO();
		probeDto.setX(2);
		probeDto.setY(3);
		probeDto.setDirection(CardinalDirection.N);
		String json = gson.toJson(probeDto);
		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/plateaus/1/probes").contentType("application/json;charset=UTF-8").content(json);
		ResultActions result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isCreated());
		result.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id", org.hamcrest.Matchers.is(1)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.x", org.hamcrest.Matchers.is(2)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.y", org.hamcrest.Matchers.is(3)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.direction", org.hamcrest.Matchers.is("N")));
	}

	@Test
	public void getProbeShouldFindProbeByIdAndPlateauIdVerifyProbeExistenceAndReturnConvertedProbe() throws Exception {
		Probe probe = Mockito.mock(Probe.class);
		Mockito.when(probeRepository.findByIdAndPlateauId(2, 1)).thenReturn(probe);
		ProbeDTO savedDto = new ProbeDTO();
		savedDto.setId(1);
		savedDto.setX(2);
		savedDto.setY(3);
		savedDto.setDirection(CardinalDirection.N);
		Mockito.when(probeConverter.toDTO(probe)).thenReturn(savedDto);

		InOrder order = Mockito.inOrder(probeRepository, validationUtil, probeConverter);

		MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/plateaus/1/probes/2");
		ResultActions result = this.mockMvc.perform(get);
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id", org.hamcrest.Matchers.is(1)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.x", org.hamcrest.Matchers.is(2)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.y", org.hamcrest.Matchers.is(3)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.direction", org.hamcrest.Matchers.is("N")));

		// ordem é importante pq so deve converter depois de validar existencia
		order.verify(probeRepository).findByIdAndPlateauId(2, 1);
		order.verify(validationUtil).ensureExistence(probe);
		order.verify(probeConverter).toDTO(probe);
	}

	@Test
	public void getProbesShouldFindProbesByPlateauIdVerifyProbeExistenceAndReturnConvertedProbe() throws Exception {
		Probe probe = Mockito.mock(Probe.class);
		Mockito.when(probeRepository.findByPlateauId(1)).thenReturn(Arrays.asList(probe));
		ProbeDTO savedDto = new ProbeDTO();
		savedDto.setId(1);
		savedDto.setX(2);
		savedDto.setY(3);
		savedDto.setDirection(CardinalDirection.N);
		Mockito.when(probeConverter.toDTO(probe)).thenReturn(savedDto);

		MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/plateaus/1/probes");
		ResultActions result = this.mockMvc.perform(get);
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		result.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(1)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", org.hamcrest.Matchers.is(1)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$[0].x", org.hamcrest.Matchers.is(2)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$[0].y", org.hamcrest.Matchers.is(3)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$[0].direction", org.hamcrest.Matchers.is("N")));
	}

	@Test
	public void postCommandSequenceShouldFindProbeByIdAndPlateauIdVerifyProbeExistenceAndExecuteCommands() throws Exception {
		Plateau plateau = new Plateau();
		Probe probe = Mockito.mock(Probe.class);
		Mockito.when(probe.getPlateau()).thenReturn(plateau);
		Mockito.when(probeRepository.findByIdAndPlateauId(2, 1)).thenReturn(probe);

		InOrder order = Mockito.inOrder(validationUtil, probe, probeRepository);

		CommandSequenceDTO commands = new CommandSequenceDTO();
		commands.getCommands().add(NavigationCommand.L);
		String json = gson.toJson(commands);
		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/plateaus/1/probes/2/command-sequence").contentType("application/json;charset=UTF-8").content(json);
		ResultActions result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.commands[0]", org.hamcrest.Matchers.is("L")));

		order.verify(validationUtil).ensureExistence(probe);
		order.verify(probe).executeCommands(argumentCaptor.capture());
		order.verify(probeRepository).save(probe);

		Assert.assertEquals(1, argumentCaptor.getValue().size());
		Assert.assertEquals(NavigationCommand.L, argumentCaptor.getValue().get(0));
	}
	
	@Test
	public void postInvalidProbeShouldFail() throws Exception{
		//garante que o @Valid esta sendo aplicado
		ProbeDTO probeDto = new ProbeDTO();
		probeDto.setX(-1);
		probeDto.setY(3);
		probeDto.setDirection(CardinalDirection.N);
		String json = gson.toJson(probeDto);
		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/plateaus/1/probes").contentType("application/json;charset=UTF-8").content(json);
		ResultActions result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
