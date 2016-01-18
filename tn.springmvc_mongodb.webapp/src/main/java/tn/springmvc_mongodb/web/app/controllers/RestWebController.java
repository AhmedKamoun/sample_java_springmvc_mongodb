package tn.springmvc_mongodb.web.app.controllers;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tn.springmvc_mongodb.web.exceptions.TestException;

@RestController
public class RestWebController {

	private static final Logger LOGGER = Logger.getLogger(RestWebController.class);

	@RequestMapping(value = "/admin/index", method = RequestMethod.GET)
	public String admin_index() {
		return "/resources/index.html";
	}

	@RequestMapping(value = "/exception", method = RequestMethod.GET)
	public String testException(long id) {
		if (id < 0)
			throw new TestException("negative number");
		if (id > 10)
			throw new NullPointerException("x > 10");

		return "HELLO WORLD";
	}

}
