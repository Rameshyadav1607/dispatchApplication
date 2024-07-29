package com.iocl.dispatchapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iocl.dispatchapplication.model.MstUser;
import com.iocl.dispatchapplication.requestdto.MstUserRequestDTO;
import com.iocl.dispatchapplication.service.MstUserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/mstUser")
public class MstUserController {

    private final MstUserService mstUserService;

    @Autowired
    public MstUserController(MstUserService mstUserService) {
        this.mstUserService = mstUserService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody MstUserRequestDTO mstUserRequestDTO,HttpServletRequest httpServletRequest) {
        return mstUserService.createUser(mstUserRequestDTO,httpServletRequest);
    }
    @PutMapping("/updateByUserId/{locCode}/{userId}")
    public ResponseEntity<?> updateUserByUserId(
            @PathVariable String locCode,
            @PathVariable String userId,
            @RequestBody MstUserRequestDTO updateRequest) {
        return mstUserService.updateUserByUserId(locCode, userId, updateRequest);
    }

    @PutMapping("/updateByMobileNumber/{mobileNumber}")
    public ResponseEntity<?> updateUserByMobileNumber(
            @PathVariable Long mobileNumber,
            @RequestBody MstUserRequestDTO updateRequest) {
        return mstUserService.updateUserByMobileNumber(mobileNumber, updateRequest);
    }
    @GetMapping("/location/{locCode}")
    public ResponseEntity<List<MstUser>> getUsersByLocCode(@PathVariable String locCode) {
        List<MstUser> users = mstUserService.getUsersByLocCode(locCode);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/delete/{locCode}/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String locCode, @PathVariable String userId) {
        return mstUserService.deleteUser(locCode, userId);
    }
}
