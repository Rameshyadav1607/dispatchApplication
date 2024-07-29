package com.iocl.dispatchapplication.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.iocl.dispatchapplication.jwt.JwtUtils;
import com.iocl.dispatchapplication.responsedto.ProfileResponse;
import com.iocl.dispatchapplication.service.DispatchEmployeeLoginService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/dispatch")
public class DispatchEmployeeLoginController {
	
	private DispatchEmployeeLoginService dispatchEmployeeLoginService;
	private JwtUtils jwtUtils;
	public DispatchEmployeeLoginController(DispatchEmployeeLoginService dispatchEmployeeLoginService,
			JwtUtils jwtUtils) {
		this.dispatchEmployeeLoginService = dispatchEmployeeLoginService;
		this.jwtUtils = jwtUtils;
	}
	
	private static final Logger logger=LoggerFactory.getLogger(DispatchEmployeeLoginService.class);
	
	    @PostMapping("/sendotp")
	    public ResponseEntity<?> sendOtp(@RequestParam Long mobileNumber) throws IOException, URISyntaxException, InterruptedException {
	      logger.info("mobile number is received:"+ mobileNumber) ; 
		  return dispatchEmployeeLoginService.sendOtp(mobileNumber);
	    }
	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestParam Long mobileNumber, @RequestParam int otp, HttpServletResponse response) {
	        String jwt = dispatchEmployeeLoginService.verifyOtpAndGenerateJwt(mobileNumber, otp);

	        if (jwt != null && jwt.startsWith("eyJ")) {
	            // Assuming that valid JWT starts with "JWT", you may need a different check
	            ResponseCookie jwtCookie = jwtUtils.createJwtCookie(jwt);
	            return ResponseEntity.ok()
	                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
	                    .body("Login successful");
	        } else {
	            return ResponseEntity.status(401).body(jwt); // Use the message from service method
	        }
	    }
	    
	    @GetMapping("/profile")
	    public ResponseEntity<ProfileResponse> getProfile() {
	        return dispatchEmployeeLoginService.getProfile();
	    }
	    
	    @PostMapping("/signout")
	    public ResponseEntity<?> logout() {
	        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
	        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("You've been signed out!");
	    }
}
