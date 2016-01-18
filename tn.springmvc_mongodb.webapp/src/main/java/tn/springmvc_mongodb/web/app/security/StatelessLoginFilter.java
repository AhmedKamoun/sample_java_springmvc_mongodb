package tn.springmvc_mongodb.web.app.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import tn.springmvc_mongodb.dto.LoginDTO;
import tn.springmvc_mongodb.web.exceptions.FailureResponse;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final TokenAuthenticationService tokenAuthenticationService;
	private final SecurityUserDetailsService userDetailsService;

	public StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService,
			SecurityUserDetailsService userDetailsService, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(urlMapping));
		this.userDetailsService = userDetailsService;
		this.tokenAuthenticationService = tokenAuthenticationService;
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
			throws AuthenticationException, IOException, ServletException {
		try {
			// get username/password from json post
			final LoginDTO admin = new ObjectMapper().readValue(httpRequest.getInputStream(), LoginDTO.class);
			final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
					admin.username, admin.password);
			return getAuthenticationManager().authenticate(loginToken);

		} catch (Exception ex) {
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
			httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			ObjectMapper mapper = new ObjectMapper();
			FailureResponse error = new FailureResponse(httpRequest.getMethod(), httpRequest.getRequestURI(),
					ex.getMessage());
			httpResponse.getWriter().println(mapper.writeValueAsString(error));

			return null;
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {

		// Lookup the complete User object from the database and create an
		// Authentication for it
		final UserDetails authenticatedUser = userDetailsService.loadUserByUsername(authentication.getName());

		final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

		// Add the custom token as HTTP header to the response
		tokenAuthenticationService.addAuthentication(response, userAuthentication);

		// Add the authentication to the Security context
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);

	}
}
