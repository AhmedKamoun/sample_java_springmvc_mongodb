package tn.springmvc_mongodb.web.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 * 
 * @author Kamoun
 * @category A controller advice allows you to use exactly the same exception
 *           handling techniques but apply them across the whole application,
 *           not just to an individual controller
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	private static final Logger LOGGER = Logger.getLogger(GlobalDefaultExceptionHandler.class);

	@ExceptionHandler(TestException.class)
	@ResponseBody
	public ResponseEntity<FailureResponse> errorHandler(HttpServletRequest req, Exception exc) {
		LOGGER.error(exc.getMessage(), exc);
		return new ResponseEntity<>(new FailureResponse(req.getRequestURI(), exc.getMessage()), HttpStatus.CONFLICT);
	}

}
