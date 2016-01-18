package tn.springmvc_mongodb.web.app.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import tn.springmvc_mongodb.web.exceptions.FailureResponse;

public class StatelessAuthenticationFilter extends GenericFilterBean {

	private static final Logger LOGGER = Logger.getLogger(StatelessAuthenticationFilter.class);
	private TokenAuthenticationService authenticationService;

	public StatelessAuthenticationFilter(TokenAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		try {
			Authentication authentication = authenticationService.getAuthentication(httpRequest);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(httpRequest, httpResponse);

		} catch (SignatureException exception) {
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			ObjectMapper mapper = new ObjectMapper();

			FailureResponse error = new FailureResponse(httpRequest.getMethod(), httpRequest.getRequestURI(),
					"unauthorized JWT (invalid signature or expired token). Please use auth api to take another valid token.");
			httpResponse.getWriter().println(mapper.writeValueAsString(error));

			SecurityContextHolder.clearContext();

		} catch (MalformedJwtException exception) {
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			ObjectMapper mapper = new ObjectMapper();

			FailureResponse error = new FailureResponse(httpRequest.getMethod(), httpRequest.getRequestURI(),
					"malformed token. Please use auth api to take another valid token.");
			httpResponse.getWriter().println(mapper.writeValueAsString(error));

			SecurityContextHolder.clearContext();

		} catch (Exception exception) {
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			ObjectMapper mapper = new ObjectMapper();

			FailureResponse error = new FailureResponse(httpRequest.getMethod(), httpRequest.getRequestURI(),
					"Internal server error, please contact the backend team.");
			httpResponse.getWriter().println(mapper.writeValueAsString(error));
			LOGGER.error(exception.getMessage(), exception);
			SecurityContextHolder.clearContext();

		}
	}

}