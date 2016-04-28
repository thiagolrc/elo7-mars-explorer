package com.elo7.marsexplorer.controller.advice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.elo7.marsexplorer.exception.BadRequestException;
import com.elo7.marsexplorer.exception.ResourceNotFoundException;

@RestController
public class ControllerForAdviceTest {

	@RequestMapping(value = "failWithBadRequest", method = RequestMethod.GET)
	public void failWithBadRequest() {
		throw new BadRequestException("teste bad request");
	}

	@RequestMapping(value = "failWithResourceNotFound", method = RequestMethod.GET)
	public void failWithResourceNotFound() {
		throw new ResourceNotFoundException();
	}

	@RequestMapping(value = "failWithException", method = RequestMethod.GET)
	public void failWithException() {
		throw new RuntimeException();
	}
}
