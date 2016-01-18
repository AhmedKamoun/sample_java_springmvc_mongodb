package tn.springmvc_mongodb.web.app.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tn.springmvc_mongodb.web.exceptions.FailureResponse;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    private static final Logger LOGGER = Logger.getLogger(MainController.class);


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "/resources/index.html";
    }

    /**
     * @param request
     * @return used to handle default error response by servlet container like
     * 403, 404... errors. see WEB-INF/web.xml for more details
     */
    @ResponseBody
    @RequestMapping(path = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public FailureResponse handle(HttpServletRequest request) {

        // request.getAttribute("javax.servlet.error.status_code")

        String message = (String) request.getAttribute("javax.servlet.error.message");
        return new FailureResponse(request.getMethod(), request.getRequestURI(), message);
    }

    /**
     * @param request
     * @return used to handle 500 internal server errors. see WEB-INF/web.xml
     * for more details
     */
    @ResponseBody
    @RequestMapping(path = "/internal-server-error", produces = MediaType.APPLICATION_JSON_VALUE)
    public FailureResponse handleInternalServerError(HttpServletRequest request) {

        // request.getAttribute("javax.servlet.error.status_code")

        return new FailureResponse(request.getMethod(), request.getRequestURI(),
                "Internal server error, please contact the backend team.");
    }
}
