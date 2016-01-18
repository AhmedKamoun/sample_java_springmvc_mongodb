package tn.springmvc_mongodb.web.app.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthenticationService {
	private static final String AUTH_HEADER_NAME = "Authorization";

	@Autowired
	private TokenHandler tokenHandler;
	
	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {

		final User user = (User) authentication.getDetails();
		response.addHeader(AUTH_HEADER_NAME, tokenHandler.createToken(user));
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);

		if (token != null) {
			final User user = tokenHandler.parseAdminFromToken(token);
			if (user != null) {
				return new UserAuthentication(user);
			}
		}
		return null;
	}
}
