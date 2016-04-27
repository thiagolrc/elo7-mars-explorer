package com.elo7.marsexplorer.controller;

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
import com.elo7.marsexplorer.probe.CardinalDirection;
import com.elo7.marsexplorer.probe.CommandSequenceDTO;
import com.elo7.marsexplorer.probe.NavigationCommand;
import com.elo7.marsexplorer.probe.Plateau;
import com.elo7.marsexplorer.probe.Probe;
import com.elo7.marsexplorer.probe.ProbeDTO;
import com.elo7.marsexplorer.repository.PlateauRepository;
import com.elo7.marsexplorer.repository.ProbeRepository;
import com.elo7.marsexplorer.validation.ValidationUtil;
import com.google.gson.Gson;

/**
 * Teste para {@link Controller}
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ControllerTest {

	private MockMvc mockMvc;

	private Gson gson = new Gson();

	@Mock
	private PlateauRepository plateauRepository;
	@Mock
	private ProbeRepository probeRepository;
	@Mock
	private ValidationUtil validationUtil;
	@Mock
	private ProbeConverter probeConverter;
	@Captor
	private ArgumentCaptor<List<NavigationCommand>> argumentCaptor;

	@Before
	public void setup() {
		Controller controller = new Controller();
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(controller, "plateauRepository", plateauRepository);
		ReflectionTestUtils.setField(controller, "probeRepository", probeRepository);
		ReflectionTestUtils.setField(controller, "validationUtil", validationUtil);
		ReflectionTestUtils.setField(controller, "probeConverter", probeConverter);
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

	}

	@Test
	public void postPlateauShouldSavePlateauAndReturnSavedObject() throws Exception {
		Plateau plateau = new Plateau(3, 4);
		ReflectionTestUtils.setField(plateau, "id", 1);
		Mockito.when(plateauRepository.save(Matchers.any(Plateau.class))).thenReturn(plateau);

		String json = gson.toJson(plateau);
		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/plateaus").contentType("application/json;charset=UTF-8").content(json);
		ResultActions result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isCreated());
		result.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id", org.hamcrest.Matchers.is(1)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.x", org.hamcrest.Matchers.is(3)));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.y", org.hamcrest.Matchers.is(4)));
	}

	@Test
	public void postProbleShouldLoadPlateauVerifyItsExistenceConvertDoEntitySaveAndReturnConvertedDto() throws Exception {
		Plateau plateau = new Plateau();
		Mockito.when(plateauRepository.findOne(1)).thenReturn(plateau);
		Probe probe = new Probe();
		Mockito.when(probeConverter.fromDTO(Matchers.any(ProbeDTO.class), Matchers.eq(plateau))).thenReturn(probe);
		ProbeDTO savedDto = new ProbeDTO();
		savedDto.setId(1);
		savedDto.setX(2);
		savedDto.setY(3);
		savedDto.setDirection(CardinalDirection.N);
		Mockito.when(probeRepository.save(probe)).thenReturn(probe);
		Mockito.when(probeConverter.toDTO(Matchers.eq(probe))).thenReturn(savedDto);
		
		InOrder order = Mockito.inOrder(validationUtil,probeRepository);

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

		order.verify(validationUtil).ensureExistence(plateau);
		order.verify(probeRepository).save(probe);
	}

	@Test
	public void postCommandSequenceShouldLoadPlateauAndProbeVerifyTheirExistenceAndExecuteCommands() throws Exception {
		Plateau plateau = new Plateau();
		Mockito.when(plateauRepository.findOne(1)).thenReturn(plateau);
		Probe probe = Mockito.mock(Probe.class);
		Mockito.when(probe.getPlateau()).thenReturn(plateau);
		Mockito.when(probeRepository.findOne(2)).thenReturn(probe);
		
		InOrder order = Mockito.inOrder(validationUtil,probe,probeRepository);

		CommandSequenceDTO commands = new CommandSequenceDTO();
		commands.getCommands().add(NavigationCommand.L);
		String json = gson.toJson(commands);
		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/plateaus/1/probes/2/command-sequence").contentType("application/json;charset=UTF-8").content(json);
		ResultActions result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.commands[0]", org.hamcrest.Matchers.is("L")));

		order.verify(validationUtil).ensureExistence(plateau);
		order.verify(validationUtil).ensureExistence(probe);
		order.verify(validationUtil).ensureTrue(true);
		order.verify(probe).executeCommands(argumentCaptor.capture());
		order.verify(probeRepository).save(probe);

		Assert.assertEquals(1, argumentCaptor.getValue().size());
		Assert.assertEquals(NavigationCommand.L, argumentCaptor.getValue().get(0));
	}
}
