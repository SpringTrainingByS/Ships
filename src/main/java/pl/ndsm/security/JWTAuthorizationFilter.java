package pl.ndsm.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println("Dobra filtruje");
		
		String header = request.getHeader(SecurityConstants.HEADER_STRING);
		
		System.out.println("header token" + header);
		
		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			System.out.println("Token nie przeszed³ wymagañ.");
			chain.doFilter(request, response);
		}
		else {
			System.out.println("Tokenowi siê uda³o przejœæ");
			UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
		}
		
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		
		if (token != null) {
			
			String user = Jwts.parser()
					.setSigningKey(SecurityConstants.SECRET.getBytes())
					.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
					.getBody()
					.getSubject();
			
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
		} 
		
		return null;
		
	}
	
	
	
}
