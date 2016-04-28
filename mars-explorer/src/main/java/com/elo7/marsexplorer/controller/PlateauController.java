package com.elo7.marsexplorer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elo7.marsexplorer.domain.Plateau;
import com.elo7.marsexplorer.repository.PlateauRepository;
import com.elo7.marsexplorer.validation.ResourceValidationUtil;

/**
 * Controller que implementas os métodos http disponíveis para o recuro {@link Plateau}
 *
 */
@RestController
@RequestMapping("/plateaus")
public class PlateauController {

	// TODO Inserir camada de validação

	@Autowired
	private PlateauRepository plateauRepository;

	@Autowired
	private ResourceValidationUtil validationUtil;

	/**
	 * Cria um {@link Plateau}
	 * 
	 * @param plateau
	 * @return {@link Plateau} criado já com seu ID
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Plateau postPlateau(@RequestBody @Valid Plateau plateau) {
		return plateauRepository.save(plateau);
	}

	/**
	 * Busca o {@link Plateau} identificado pelo seu ID
	 * 
	 * @param plateauId
	 * @return {@link Plateau} identificado pelo ID ou {@link HttpStatus#NOT_FOUND} caso este não exista
	 */
	@RequestMapping(value = "/{plateauId}", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public Plateau getPlateau(@PathVariable("plateauId") int plateauId) {
		Plateau plateau = plateauRepository.findOne(plateauId);
		validationUtil.ensureExistence(plateau);
		return plateau;
	}

	/**
	 * Lista todos os {@link Plateau} existentes
	 * 
	 * @return {@link List} de {@link Plateau} contendo os planaltos cadastrados ou lista vazia caso não haja cadastros
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public List<Plateau> getAllPlateau() {
		return plateauRepository.findAll();
	}

}
