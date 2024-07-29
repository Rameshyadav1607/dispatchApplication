package com.iocl.dispatchapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iocl.dispatchapplication.exception.UserNotFoundException;
import com.iocl.dispatchapplication.jwt.JwtUtils;
import com.iocl.dispatchapplication.model.MstUser;
import com.iocl.dispatchapplication.model.MstUserPK;
import com.iocl.dispatchapplication.repository.MstUserRepository;
import com.iocl.dispatchapplication.requestdto.MstUserRequestDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MstUserService {

    private MstUserRepository mstUserRepository;
    private JwtUtils jwtUtils;
    
    public MstUserService(MstUserRepository mstUserRepository, JwtUtils jwtUtils) {
		super();
		this.mstUserRepository = mstUserRepository;
		this.jwtUtils = jwtUtils;
	}
	public ResponseEntity<?> createUser(MstUserRequestDTO mstUserRequestDTO,HttpServletRequest httpServletRequest) {
    	
    	             String   token=jwtUtils.getJwtFromCookies(httpServletRequest);
    	             
    	             String  locCode=jwtUtils.extractLocCode(token);
    	             String   username= jwtUtils.extractUserId(token);
    	
        // Check if user with the given userId already exists
        Optional<MstUser> existingUserById = mstUserRepository.findById(new MstUserPK(mstUserRequestDTO.getLocCode(), mstUserRequestDTO.getUserId()));
        if (existingUserById.isPresent()) {
            return new ResponseEntity<>("User with this userId already exists", HttpStatus.CONFLICT);
        }

        // Check if user with the given mobileNumber already exists
        Optional<MstUser> existingUserByMobile = mstUserRepository.findByMobileNumber(mstUserRequestDTO.getMobileNumber());
        if (existingUserByMobile.isPresent()) {
            return new ResponseEntity<>("User with this mobile number already exists", HttpStatus.CONFLICT);
        }

        // Create new user
        MstUser user = new MstUser();
        user.setLocCode(mstUserRequestDTO.getLocCode());
        user.setUserId(mstUserRequestDTO.getUserId());
        user.setUserName(mstUserRequestDTO.getUserName());
        user.setRoleId(mstUserRequestDTO.getRoleId());
        user.setMobileNumber(mstUserRequestDTO.getMobileNumber());

        // Set default values for new user
        user.setCreatedBy(username); // Replace with actual logged-in user

        MstUser createdUser = mstUserRepository.save(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @Transactional
    public ResponseEntity<?> updateUserByUserId(String locCode, String userId, MstUserRequestDTO updateRequest) {
        Optional<MstUser> existingUser = mstUserRepository.findById(new MstUserPK(locCode, userId));
        if (existingUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        MstUser user = existingUser.get();
        user.setUserName(updateRequest.getUserName());
        user.setRoleId(updateRequest.getRoleId());
        user.setMobileNumber(updateRequest.getMobileNumber());
        user.setLastUpdatedDate(LocalDateTime.now());

        MstUser updatedUser = mstUserRepository.save(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateUserByMobileNumber(Long mobileNumber, MstUserRequestDTO updateRequest) {
        Optional<MstUser> existingUser = mstUserRepository.findByMobileNumber(mobileNumber);
        if (existingUser.isEmpty()) {
            return new ResponseEntity<>("User not found with this mobile number", HttpStatus.NOT_FOUND);
        }

        MstUser user = existingUser.get();
        user.setUserName(updateRequest.getUserName());
        user.setRoleId(updateRequest.getRoleId());
        user.setMobileNumber(updateRequest.getMobileNumber());
        user.setLastUpdatedDate(LocalDateTime.now());

        MstUser updatedUser = mstUserRepository.save(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
  
    public List<MstUser> getUsersByLocCode(String locCode) {
        return mstUserRepository.findByLocCode(locCode);
    }
    
    public ResponseEntity<?> deleteUser(String locCode, String userId) {
        try {
            MstUser existingUser = mstUserRepository.findById(new MstUserPK(locCode, userId))
                    .orElseThrow(() -> new UserNotFoundException("User not found with locCode: " + locCode + " and userId: " + userId));
            existingUser.setStatus("D");
            mstUserRepository.save(existingUser);
            return ResponseEntity.ok("User deleted successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
}
}
