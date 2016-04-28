package com.elo7.marsexplorer.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elo7.marsexplorer.converter.ProbeConverter;
import com.elo7.marsexplorer.domain.NavigationCommand;
import com.elo7.marsexplorer.domain.Plateau;
import com.elo7.marsexplorer.domain.Probe;
import com.elo7.marsexplorer.dto.CommandSequenceDTO;
import com.elo7.marsexplorer.dto.ProbeDTO;
import com.elo7.marsexplorer.repository.ProbeRepository;
import com.elo7.marsexplorer.validation.ResourceValidationUtil;

/**
 * Controller que implementas os métodos http disponíveis para o recuro {@link ProbeDTO}
 *
 */
@RestController
@RequestMapping("/plateaus/{plateauId}")
public class ProbeController {
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
	public ProbeDTO postProbe(@PathVariable("plateauId") int plateauId, @RequestBody ProbeDTO probe) {
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
		// TODO Tem cara de que deveria ser assincrono, mas teria que criar fila, executar em ordem e evitar concorrência
		Probe probe = probeRepository.findByIdAndPlateauId(probeId, plateauId);
		validationUtil.ensureExistence(probe);

		probe.executeCommands(commandSequence.getCommands());// TODO ta esquisito isso aqui

		probeRepository.save(probe);

		return commandSequence;// TODO Talvez devesse voltar uma lista com o resultado de cada comando
	}

}
