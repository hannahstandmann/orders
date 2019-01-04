package org.smbaiwsy.config;

import org.smbaiwsy.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Filter that tries to parse authentication header and authorize user on given
 * authentication manager.
 * 
 * @author anamattuzzi-stojanovic
 *
 */
public class SessionAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;


	@Autowired
	private Map<String, String> sessionMapping;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(AppConstants.SESSION_STRING);
		String username = null;
		if (header != null) {
			username = sessionMapping.get(header);
		} else {
			logger.warn("Couldn't find the user, ignoring the header");
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			logger.info(userDetails.getUsername());
			logger.info(req.getSession().getId() + " " + header);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
			logger.info("Authenticated user " + username + ", Setting security context");
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		chain.doFilter(req, res);
	}
}
