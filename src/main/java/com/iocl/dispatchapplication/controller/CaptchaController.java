package com.iocl.dispatchapplication.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iocl.dispatchapplication.requestdto.EmployeeloginRequest;
import com.iocl.dispatchapplication.responsedto.CaptchaResponseData;
import com.iocl.dispatchapplication.service.CaptchaService;

@RestController
@RequestMapping("/captch")
public class CaptchaController {

    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @GetMapping("/get-captcha")
    public ResponseEntity<CaptchaResponseData> getCaptcha() throws IOException {
    	System.out.println("from /get-captcha");
        return captchaService.getCaptcha();
    }

    @PostMapping("/check-captcha")
    public ResponseEntity<?> checkCaptcha(@RequestBody EmployeeloginRequest employeeLoginModal) {
        return captchaService.checkCaptcha(employeeLoginModal);
    }
}
