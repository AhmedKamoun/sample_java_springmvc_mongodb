package tn.springmvc_mongodb.web.config.root;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import tn.springmvc_mongodb.web.app.security.SecurityUserDetailsService;
import tn.springmvc_mongodb.web.app.security.StatelessAuthenticationFilter;
import tn.springmvc_mongodb.web.app.security.StatelessLoginFilter;
import tn.springmvc_mongodb.web.app.security.TokenAuthenticationService;

/**
 *
 * The Spring Security configuration for the application - its a jwt
 * authentication based.
 *
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = Logger.getLogger(AppSecurityConfig.class);

	@Autowired
	private SecurityUserDetailsService userDetailsService;
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.anonymous().and().servletApi().and().headers().cacheControl();
		http.authorizeRequests()
				// allow anonymous GETs to Welcome page
				.antMatchers(HttpMethod.GET, "/").permitAll()

				// Allow anonymous resource requests
				.antMatchers("/resources/**").permitAll()

				// allow anonymous POSTs to login
				.antMatchers(HttpMethod.POST, "/api/login").permitAll()

				// defined Admin only API area
				.antMatchers("/admin/**").hasRole("ADMIN")

				// all other request need to be authenticated
				.anyRequest().hasRole("USER").and()

				/**
				 * custom JSON based authentication by POST of {"username":
				 * "<name>","password":"<password>"} which sets the token header
				 * upon authentication
				 */
				.addFilterBefore(new StatelessLoginFilter("/api/login", tokenAuthenticationService, userDetailsService,
						authenticationManager()), UsernamePasswordAuthenticationFilter.class)

				/**
				 * Custom Token based authentication based on the header
				 * previously given to the client
				 */
				.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService),
						UsernamePasswordAuthenticationFilter.class);

		if ("true".equals(System.getProperty("httpsOnly"))) {
			LOGGER.info("launching the application in HTTPS-only mode");
			http.requiresChannel().anyRequest().requiresSecure();
		}
	}
}
