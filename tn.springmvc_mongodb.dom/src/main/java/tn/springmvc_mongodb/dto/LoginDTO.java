package tn.springmvc_mongodb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * JSON-serializable DTO containing user login data
 *
 */
public class LoginDTO {
	@JsonProperty
	public String username;
	@JsonProperty
	public String password;

}
