package com.iocl.dispatchapplication.requestdto;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class EmployeeloginRequest {
	 private Integer id;
	 private String password;
	 private String captcha_value;

}
