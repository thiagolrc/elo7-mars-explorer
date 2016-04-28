package com.elo7.marsexplorer.controller;

import java.util.List;

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

@RestController
@RequestMapping("/plateaus")
public class PlateauController {

	// TODO Inserir camada de validação

	// TODO Tratar excecoes Illegal e resourceNotFound

	@Autowired
	private PlateauRepository plateauRepository;

	@Autowired
	private ResourceValidationUtil validationUtil;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Plateau postPlateau(@RequestBody Plateau plateau) {
		return plateauRepository.save(plateau);
	}

	@RequestMapping(value = "/{plateauId}", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public Plateau getPlateau(@PathVariable("plateauId") int plateauId) {
		Plateau plateau = plateauRepository.findOne(plateauId);
		validationUtil.ensureExistence(plateau);
		return plateau;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public List<Plateau> getAllPlateau() {
		return plateauRepository.findAll();
	}

}
