package org.smbaiwsy.config;

import org.smbaiwsy.rest.dto.ErrorResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Used to handle authentication failure and build HTTP UNAUTHORIZED 401
 * response for client.
 * 
 * @author anamattuzzi-stojanovic
 *
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		// response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		log.debug("Authentication required");
		String json = new ObjectMapper()
				.writeValueAsString(new ErrorResponse("CREDENTIALS_REJECTED", authException.getMessage()));
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader("WWW-Authenticate", "Bearer realm=orders");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
}