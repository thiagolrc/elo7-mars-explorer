package com.elo7.marsexplorer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elo7.marsexplorer.probe.Plateau;

@RestController
@RequestMapping()
public class Controller {

	@RequestMapping(value = "/plateau", method = RequestMethod.POST)
	@ResponseStatus(code=HttpStatus.CREATED)
	public Plateau post(@RequestBody Plateau plateau) {
		return plateau;
	}

}
