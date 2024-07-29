package com.iocl.dispatchapplication.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.iocl.dispatchapplication.jwt.JwtUtils;
import com.iocl.dispatchapplication.model.CaptchaEntity;
import com.iocl.dispatchapplication.model.MstEmployee;
import com.iocl.dispatchapplication.model.MstUser;
import com.iocl.dispatchapplication.model.RefSequence;
import com.iocl.dispatchapplication.repository.CaptchaRepository;
import com.iocl.dispatchapplication.repository.MstEmployeeRepository;
import com.iocl.dispatchapplication.repository.MstUserRepository;
import com.iocl.dispatchapplication.repository.RefSequenceRepository;
import com.iocl.dispatchapplication.requestdto.EmployeeloginRequest;
import com.iocl.dispatchapplication.responsedto.ProfileResponse;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class LoginService {
	
	private MstEmployeeRepository mstEmployeeRepository;
	private CaptchaRepository captchaRepository;
	private MstUserRepository mstUserRepository;
	private RefSequenceRepository refSequenceRepository;
	private JwtUtils jwtUtils;
	private AuthenticationManager authenticationManager;
	public LoginService(MstEmployeeRepository mstEmployeeRepository, CaptchaRepository captchaRepository,
			MstUserRepository mstUserRepository, RefSequenceRepository refSequenceRepository, JwtUtils jwtUtils,
			AuthenticationManager authenticationManager) {
		this.mstEmployeeRepository = mstEmployeeRepository;
		this.captchaRepository = captchaRepository;
		this.mstUserRepository = mstUserRepository;
		this.refSequenceRepository = refSequenceRepository;
		this.jwtUtils = jwtUtils;
		this.authenticationManager = authenticationManager;
	}
	
	private static final Logger logger=LoggerFactory.getLogger(LoginService.class);

	   public String getRole(MstEmployee employee) {
		      Optional<MstUser> userOptional =mstUserRepository.findByUserId(appendZeros(String.valueOf(employee.getEmpCode())));
		        
		        if (userOptional.isPresent()) {
		            MstUser user = userOptional.get();
		            String roleId = user.getRoleId();
		            
		            if ("ADMIN".equals(roleId)) {
		                return "ADMIN";
		            } else if ("LOC_ADMIN".equals(roleId)) {
		                return "LOC_ADMIN";
		            } else if ("DISPATCH".equals(roleId)) {
		                return "DISPATCH";
		            } else {
		                return "EMPLOYEE"; // or any default role you want to set
		            }
		        }
		        
		        return "EMPLOYEE"; // Default role if user is not found
		    }//getRole completed

	    public String appendZeros(String id) {
	        while (id.length() < 8) {
	            id = "0" + id;
	        }
	        return id;
	    }

		public ResponseEntity<?> login(EmployeeloginRequest employeeloginRequest,
				HttpServletResponse httpServletResponse) {
			// TODO Auto-generated method stub
			 logger.info("Received captcha value: " + employeeloginRequest.getCaptcha_value());

		        Optional<CaptchaEntity> captchaEntity = captchaRepository.findByValue(employeeloginRequest.getCaptcha_value());
                 System.out.println(captchaEntity);
		        if (captchaEntity.isEmpty()) {
		            logger.info("Captcha value not found in the database.");
		            return ResponseEntity.status(400).body("Invalid or expired captcha");
		        }

		        CaptchaEntity captcha = captchaEntity.get();
		        logger.info("Stored captcha value: " + captcha.getValue());
		        logger.info("Captcha expiry time: " + captcha.getExpiryTime());

		        if (!captcha.getValue().equals(employeeloginRequest.getCaptcha_value())) {
		            logger.info("Captcha value does not match.");
		            return ResponseEntity.status(400).body("Invalid or expired captcha");
		        }

		        if (LocalDateTime.now().isAfter(captcha.getExpiryTime())) {
		            logger.info("Captcha has expired.");
		            return ResponseEntity.status(400).body("Invalid or expired captcha");
		        }

		        // LDAP Authentication Logic
		        String username = appendZeros(String.valueOf(employeeloginRequest.getId()));
		        String password = employeeloginRequest.getPassword();

		        Hashtable<String, String> env = new Hashtable<>();
		        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		        env.put(Context.PROVIDER_URL, "LDAP://dcmkho03:389 LDAP://dcmkho1:389 LDAP://dcmkho2:389");
		        env.put(Context.SECURITY_AUTHENTICATION, "DIGEST-MD5");
		        env.put(Context.SECURITY_PRINCIPAL, username);
		        env.put(Context.SECURITY_CREDENTIALS, password);
		        try {
		            logger.info("Attempting to connect to LDAP server with username: " + username);
		            DirContext ctx = new InitialDirContext(env);
		            ctx.close();
		            logger.info("LDAP authentication successful.");

		            Optional<MstEmployee> employeeOptional = mstEmployeeRepository.findByEmpCode(employeeloginRequest.getId());
		            if (employeeOptional.isEmpty()) {
		                logger.info("User not found with ID: " + username);
		                return ResponseEntity.status(400).body("User not found");
		            }
		            MstEmployee employee = employeeOptional.get();

		            Optional<RefSequence> refSequenceOptional = refSequenceRepository.findByLocCode(employee.getLocCode());
		            if (refSequenceOptional.isEmpty()) {
		                logger.info("Application not implemented for the location: " + employee.getLocCode());
		                return ResponseEntity.status(400).body("Application not implemented for the location");
		            }

		            String role = getRole(employeeOptional.get());
//		            List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role));
		            List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(role));


		            UserDetails userDetails = User.builder()
		                .username(username)
		                .password(password)
		                .authorities(authorities)
		                .build();
		            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
		            SecurityContextHolder.getContext().setAuthentication(authentication);

		            Map<String, Object> claims = new HashMap<>();
		            claims.put("userId", employee.getEmpCode());
		            claims.put("username", employee.getEmpName());
		            claims.put("role", employee.getDesignation());
		            claims.put("locationName", employee.getLocName());

		            String jwt = jwtUtils.generateToken(username, claims);

		            ResponseCookie jwtCookie = jwtUtils.createJwtCookie(jwt);
		            httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

		            
		            Map<String, Object> response = new LinkedHashMap<>();
//		            Map<String, String> response = new HashMap<>();
		          //  response.put("jwt", jwt);
		           
		            response.put("EmpName", employee.getEmpName());
		            response.put("Designation", employee.getDesignation());
		            response.put("role", role);          
		            response.put("locationName", employee.getLocName());
		      
		            return ResponseEntity.ok(response);
		        } catch (AuthenticationException e) {
		            logger.error("LDAP Authentication failed for username: " + username + ". Possible reasons: Incorrect username or password.", e);
		            return ResponseEntity.status(401).body("LDAP Authentication failed: Invalid credentials");
		        } catch (CommunicationException e) {
		            logger.error("CommunicationException: Unable to connect to LDAP server. Possible reasons: Network issues, incorrect server URL, or server downtime.", e);
		            return ResponseEntity.status(500).body("Unable to connect to LDAP server");
		        } catch (NamingException e) {
		            logger.error("NamingException: LDAP authentication failed for username: " + username + ". Possible reasons: Configuration issues or invalid settings.", e);
		            return ResponseEntity.status(401).body("LDAP Connection failed");
		        }	
		
		}

		  public ResponseEntity<?> logout() {
		        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("You've been signed out!");
		    }
		  
		  public ResponseEntity<ProfileResponse> getEmployeeProfile() {
		        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		        String empCode = authentication.getName();  // Assuming empCode is the username

		        Optional<MstEmployee> employeeOptional =mstEmployeeRepository.findByEmpCode(Integer.parseInt(empCode));

		        if (employeeOptional.isPresent()) {
		            MstEmployee employee = employeeOptional.get();
		            ProfileResponse profile = new ProfileResponse();
		            profile.setName(employee.getEmpName());
		            profile.setDesignation(employee.getDesignation());
		            profile.setLocName(employee.getLocName());

		            return ResponseEntity.ok(profile);
		        } else {
		            return ResponseEntity.notFound().build();
		        }
		    }
	        
}
