package com.thiagolrc.marsexplorer.controller;

import static com.thiagolrc.marsexplorer.domain.NavigationCommand.L;
import static com.thiagolrc.marsexplorer.domain.NavigationCommand.M;
import static com.thiagolrc.marsexplorer.domain.NavigationCommand.R;

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

import com.google.gson.Gson;
import com.thiagolrc.marsexplorer.Application;
import com.thiagolrc.marsexplorer.controller.PlateauController;
import com.thiagolrc.marsexplorer.controller.ProbeController;
import com.thiagolrc.marsexplorer.converter.ProbeConverter;
import com.thiagolrc.marsexplorer.domain.CardinalDirection;
import com.thiagolrc.marsexplorer.domain.NavigationCommand;
import com.thiagolrc.marsexplorer.domain.Plateau;
import com.thiagolrc.marsexplorer.domain.Position;
import com.thiagolrc.marsexplorer.domain.Probe;
import com.thiagolrc.marsexplorer.dto.CommandSequenceDTO;
import com.thiagolrc.marsexplorer.dto.ProbeDTO;
import com.thiagolrc.marsexplorer.repository.ProbeRepository;
import com.thiagolrc.marsexplorer.validation.ResourceValidationUtil;

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
	private ArgumentCaptor<NavigationCommand> argumentCaptor;

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
		
		CommandSequenceDTO commands = new CommandSequenceDTO();
		commands.getCommands().addAll(Arrays.asList(L,M,R));
		
		Position p1 = Mockito.mock(Position.class);
		Mockito.when(p1.toString()).thenReturn("left");
		Mockito.when(probe.executeCommand(L)).thenReturn(p1);
		Position p2 = Mockito.mock(Position.class);
		Mockito.when(p2.toString()).thenReturn("move");
		Mockito.when(probe.executeCommand(M)).thenReturn(p2);
		Position p3 = Mockito.mock(Position.class);
		Mockito.when(p3.toString()).thenReturn("right");
		Mockito.when(probe.executeCommand(R)).thenReturn(p3);
		
		InOrder order = Mockito.inOrder(validationUtil, probe, probeRepository);

		
		
		String json = gson.toJson(commands);
		MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/plateaus/1/probes/2/command-sequence").contentType("application/json;charset=UTF-8").content(json);
		ResultActions result = this.mockMvc.perform(post);
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.commands[0]", org.hamcrest.Matchers.is("L")));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.commands[1]", org.hamcrest.Matchers.is("M")));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.commands[2]", org.hamcrest.Matchers.is("R")));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.positionChangeLog[0]", org.hamcrest.Matchers.is("left")));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.positionChangeLog[1]", org.hamcrest.Matchers.is("move")));
		result.andExpect(MockMvcResultMatchers.jsonPath("$.positionChangeLog[2]", org.hamcrest.Matchers.is("right")));

		order.verify(validationUtil).ensureExistence(probe);
		order.verify(probe,Mockito.times(3)).executeCommand(argumentCaptor.capture());
		order.verify(probeRepository).save(probe);

		Assert.assertEquals(3, argumentCaptor.getAllValues().size());
		Assert.assertEquals(NavigationCommand.L, argumentCaptor.getAllValues().get(0));
		Assert.assertEquals(NavigationCommand.M, argumentCaptor.getAllValues().get(1));
		Assert.assertEquals(NavigationCommand.R, argumentCaptor.getAllValues().get(2));
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
