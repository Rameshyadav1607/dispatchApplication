package com.iocl.dispatchapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iocl.dispatchapplication.requestdto.EmployeeloginRequest;
import com.iocl.dispatchapplication.responsedto.ProfileResponse;
import com.iocl.dispatchapplication.service.LoginService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/employee")
public class LoginController {
	@Autowired
	private LoginService loginservice;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody EmployeeloginRequest employeeloginRequest,HttpServletResponse httpServletResponse){
		return loginservice.login(employeeloginRequest,httpServletResponse);
		
	}
	
	    @PostMapping("/signout")
	    public ResponseEntity<?> logoutUser() {
	        return loginservice.logout();
	   }
	 
	   @GetMapping("/profile")
	    public ResponseEntity<ProfileResponse> getProfile() {
	        return loginservice.getEmployeeProfile();
	    }

}
