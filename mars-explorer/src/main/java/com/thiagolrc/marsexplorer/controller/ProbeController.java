package com.thiagolrc.marsexplorer.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.thiagolrc.marsexplorer.converter.ProbeConverter;
import com.thiagolrc.marsexplorer.domain.NavigationCommand;
import com.thiagolrc.marsexplorer.domain.Plateau;
import com.thiagolrc.marsexplorer.domain.Probe;
import com.thiagolrc.marsexplorer.dto.CommandSequenceDTO;
import com.thiagolrc.marsexplorer.dto.ProbeDTO;
import com.thiagolrc.marsexplorer.repository.ProbeRepository;
import com.thiagolrc.marsexplorer.validation.ResourceValidationUtil;

/**
 * Controller que implementas os métodos http disponíveis para o recuro {@link ProbeDTO}
 *
 */
@RestController
@RequestMapping("/plateaus/{plateauId}")
public class ProbeController {
	// TODO HATEOAS
	@Autowired
	private ProbeRepository probeRepository;
	@Autowired
	private ResourceValidationUtil validationUtil;
	@Autowired
	private ProbeConverter probeConverter;
	@Autowired
	private PlateauController plateuController;

	/**
	 * Cria uma {@link ProbeDTO em um determinado Plateau}
	 * 
	 * @param plateauId
	 * @param probe
	 * @return {@link ProbeDTO} criada já com seu respectivo ID.
	 */
	@RequestMapping(value = "/probes", method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProbeDTO postProbe(@PathVariable("plateauId") int plateauId, @RequestBody @Valid ProbeDTO probe) {
		Plateau plateau = plateuController.getPlateau(plateauId);

		Probe probeToSave = probeConverter.fromDTO(probe, plateau);

		return probeConverter.toDTO(probeRepository.save(probeToSave));
	}

	/**
	 * Busca uma {@link ProbeDTO} de um determinado {@link Plateau}
	 * 
	 * @param plateauId
	 * @param probeId
	 * @return A {@link ProbeDTO} encontrada ou {@link HttpStatus#NOT_FOUND} caso esta não exista.
	 */
	@RequestMapping(value = "/probes/{probeId}", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public ProbeDTO getProbe(@PathVariable("plateauId") int plateauId, @PathVariable("probeId") int probeId) {
		Probe probe = probeRepository.findByIdAndPlateauId(probeId, plateauId);
		validationUtil.ensureExistence(probe);

		return probeConverter.toDTO(probe);
	}

	/**
	 * Busca todas as {@link ProbeDTO} de um determinado {@link Plateau}
	 * 
	 * @param plateauId
	 * @return {@link List} de {@link ProbeDTO} com as sondas encontradas ou lista vazia caso nenhuma seja encontrada.
	 */
	@RequestMapping(value = "/probes", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public List<ProbeDTO> getProbes(@PathVariable("plateauId") int plateauId) {
		List<Probe> probes = probeRepository.findByPlateauId(plateauId);

		return probes.stream().map(p -> probeConverter.toDTO(p)).collect(Collectors.toList());
	}

	/**
	 * Envia uma sequencia de {@link NavigationCommand} para a sonda
	 * 
	 * @param plateauId
	 * @param probeId
	 * @param commandSequence
	 *            CommandSequenceDTO com a sequencia de comandos a serem executados
	 * @return CommandSequenceDTO com os comando executados
	 */
	@RequestMapping(value = "/probes/{probeId}/command-sequence", method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.OK)
	public CommandSequenceDTO postCommandSequence(@PathVariable("plateauId") int plateauId, @PathVariable("probeId") int probeId, @RequestBody CommandSequenceDTO commandSequence) {
		// TODO Poderia ser assincrono. Lembrar que para isso teria que criar uma fila e executar em ordem. O método teria também que ou receber uma função de callback ou devolver
		// um ticket para consulta de término da execução
		Probe probe = probeRepository.findByIdAndPlateauId(probeId, plateauId);
		validationUtil.ensureExistence(probe);

		commandSequence.getCommands().stream().forEachOrdered(c -> commandSequence.getPositionChangeLog().add(probe.executeCommand(c).toString()));

		probeRepository.save(probe);

		return commandSequence;
	}

}
