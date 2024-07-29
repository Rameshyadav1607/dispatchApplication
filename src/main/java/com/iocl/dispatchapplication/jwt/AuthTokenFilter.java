package com.iocl.dispatchapplication.jwt;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.iocl.dispatchapplication.model.MstEmployee;
import com.iocl.dispatchapplication.model.MstUser;
import com.iocl.dispatchapplication.repository.MstEmployeeRepository;
import com.iocl.dispatchapplication.repository.MstUserRepository;
import com.iocl.dispatchapplication.service.MstUserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter {
	
	  @Autowired
	    private JwtUtils jwtUtils;

	    @Autowired
	    private MstUserRepository mstUserRepository;

	    @Autowired
	    private MstEmployeeRepository mstEmployeeRepository;
	    
	    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
            String jwt = parseJwt(request);
            logger.info("JWT retrieved: {}", jwt);

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.extractUserName(jwt).trim();
                logger.info("Username retrieved from JWT: {}", username);

                // Fetch employee details using username (userId)
                Optional<MstEmployee> employeeOpt =mstEmployeeRepository.findByEmpCode(Integer.parseInt(username));
                if (employeeOpt.isPresent()) {
                    MstEmployee employee = employeeOpt.get();
                    // Use employee's userId to find the role in MstUser
                    Optional<MstUser> userEntityOpt = mstUserRepository.findByUserId(appendZeros(String.valueOf(employee.getEmpCode())));
                    // Create a UserDetails object with default role if not found
                    String role = "user"; // Default role if not found
                    if (userEntityOpt.isPresent()) {
                        MstUser userEntity = userEntityOpt.get();
                        role = userEntity.getRoleId();
                        
                        if (role == null || role.isEmpty()) {
                            logger.error("Role is null or empty for user: {}", username);
                            throw new IllegalArgumentException("Role cannot be null or empty");
                        }
                        logger.info("User found: {} with role: {}", username, role);
                    } else {
                        logger.warn("No role found for user: {}", username);
                    }

                    GrantedAuthority authority = new SimpleGrantedAuthority(role);
                    List<GrantedAuthority> authorities = Collections.singletonList(authority);


                    // Build UserDetails object with role
                    UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(username)
                    		.password("") 
                            .authorities(authorities)
                            .accountExpired(false)
                            .accountLocked(false)
                            .credentialsExpired(false)
                            .build();

                    // Create authentication token
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // Set authentication details
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set authentication in SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.error("Employee not found for username: {}", username);
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        // Proceed with the next filter in the chain
        filterChain.doFilter(request, response);
		
	}
	
	public String appendZeros(String id) {
        while (id.length() < 8) {
            id = "0" + id;
        }
        return id;
    }
    
    
    private String parseJwt(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromCookies(request);
        if (jwt == null) {
            logger.error("JWT not found in cookies");
        } else {
            logger.info("JWT found in cookies: {}", jwt);
        }
        return jwt;
    }

}
