package com.iocl.dispatchapplication.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.iocl.dispatchapplication.jwt.JwtUtils;
import com.iocl.dispatchapplication.model.MstUser;
import com.iocl.dispatchapplication.model.OtpEntity;
import com.iocl.dispatchapplication.model.RefSequence;
import com.iocl.dispatchapplication.repository.MstUserRepository;
import com.iocl.dispatchapplication.repository.OtpRepository;
import com.iocl.dispatchapplication.repository.RefSequenceRepository;
import com.iocl.dispatchapplication.responsedto.ProfileResponse;

@Service
public class DispatchEmployeeLoginService {
	
	private OtpRepository otpRepository;
	private JwtUtils jwtUtils;
	private MstUserRepository mstUserRepository;
	private HttpClient httpClient;
	private RefSequenceRepository refSequenceRepository;
	
	public DispatchEmployeeLoginService(OtpRepository otpRepository, JwtUtils jwtUtils,
			MstUserRepository mstUserRepository, HttpClient httpClient, RefSequenceRepository refSequenceRepository) {
		this.otpRepository = otpRepository;
		this.jwtUtils = jwtUtils;
		this.mstUserRepository = mstUserRepository;
		this.httpClient = httpClient;
		this.refSequenceRepository = refSequenceRepository;
	}



	private static final Logger logger=LoggerFactory.getLogger(DispatchEmployeeLoginService.class);
	
	public ResponseEntity<?> sendOtp(Long mobileNumber) throws IOException, InterruptedException, URISyntaxException{
		       Optional<MstUser>   mstuserOptional=mstUserRepository.findByMobileNumber(mobileNumber);
		       if(mstuserOptional.isPresent()) {
		    	         MstUser   mstuser=mstuserOptional.get();
		    	         generateOtp(mstuser);
		    	         logger.info("OTP sent successfully to user with ID: {}", mstuser.getUserId());

		    	          return ResponseEntity.ok().body("OTP sent");
		       }
		       else {
		            logger.warn("User not found for mobile number: {}", mobileNumber);

		            return ResponseEntity.notFound().build();
		        }
	}

	

	private void generateOtp(MstUser mstuser) throws IOException, InterruptedException, URISyntaxException {
		 logger.info("Generating OTP for user ID: {}", mstuser.getUserId());
	        OtpEntity otpEntity = new OtpEntity();
	        otpEntity.setEmpCode(mstuser.getUserId());
	        otpEntity.setOtp(ThreadLocalRandom.current().nextInt(100000, 1000000));
	        otpEntity.setCreatedAt(LocalDateTime.now());
	        otpEntity.setExpirationAt(otpEntity.getCreatedAt().plusMinutes(10));
	        otpRepository.save(otpEntity);

	        String message = "OTP for Dispatch Application login is " + otpEntity.getOtp() + ", which expires in 120 seconds -IndianOil.";
	        String url = "https://spandan.indianoil.co.in/sandesh/smsPrioOutMessageRequest/priooutgoingSMS";
	        URL urlObj = new URL(url.trim());

	        JSONArray ja = new JSONArray();
	        JSONObject obj = new JSONObject();
	      //  String formattedMobileNumber = "+91" + user.getMobileNumber();

	        obj.put("mobile_no", String.valueOf(mstuser.getMobileNumber()));
	        obj.put("sms_content", message.toString());
	        obj.put("gst_flag", "3");
	        obj.put("template_id", "20240715212008360743");
	        obj.put("ref_in_msg_unique_id", "20240715212008360743");
	        ja.put(obj);
	       
	       
	        HttpRequest request = HttpRequest.newBuilder()
	                .POST(HttpRequest.BodyPublishers.ofString(ja.toString()))
	                .uri(urlObj.toURI())
	                .setHeader("authenticationToken", "1085779bb2f0935b728713bc33e7d0ab")
	                .setHeader("Content-Type", "application/json")
	                .setHeader("appId", "GatePass")
	              //  .setHeader("appId", "DispatchApp")
	                .setHeader("appCategory", "GatePass_OTP")
	               // .setHeader("appCategory", "Dispatch_OTP")
	                .setHeader("msgType", "ENG")
	                .build();
	        request.headers().map().forEach((k, v) -> logger.info("Header: {} = {}", k, v));

	        logger.info("Request URI: {}", request.uri());
	        logger.info("Request headers: {}", request.headers());
	        logger.info("Request body: {}", ja.toString());
	        logger.info("Sending SMS request: {}", request);
	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	        logger.info("SMS API response status code: {}", response.statusCode());

	        logger.info("SMS API response: {}", response.body());

	        if (response.statusCode() == 200) {
	            logger.info("OTP successfully generated and sent for user ID: {}", mstuser.getUserId());
	        } else {
	            logger.error("Failed to send OTP. SMS API responded with status code: {}", response.statusCode());
	        }
	}
	
	 public String verifyOtpAndGenerateJwt(Long mobileNumber, int otp) {

	        logger.info("Verifying OTP for mobile number: {}", mobileNumber);



	        Optional<MstUser> userOptional = mstUserRepository.findByMobileNumber(mobileNumber);



	        if (userOptional.isPresent()) {

	            MstUser user = userOptional.get();



	            if (!"DISPATCH".equalsIgnoreCase(user.getRoleId())) {

	                logger.warn("User with role '{}' is not allowed to log in", user.getRoleId());

	                return "User not authorized for OTP-based login";

	            }



	            // Find the latest OTP for the user

	            OtpEntity otpEntity = otpRepository.findFirstByEmpCodeOrderByCreatedAtDesc(user.getUserId());



	            if (otpEntity != null && otpEntity.getOtp() == otp) {

	                if (otpEntity.getExpirationAt().isAfter(LocalDateTime.now())) {

	                    logger.info("OTP verified successfully for user ID: {}", user.getUserId());



	                    Map<String, Object> claims = new HashMap<>();

	                    claims.put("userId", user.getUserId());

	                    claims.put("username", user.getUserName());
	                    claims.put("Designation", user.getRoleId());
	                    claims.put("locName",user.getLocCode());
	                    claims.put("locCode", user.getLocCode());


	                    String jwt = jwtUtils.generateToken(user.getUserId(), claims);

	                    logger.info("JWT generated successfully for user ID: {}", user.getUserId());



	                    // Check if the location is implemented

	                    Optional<RefSequence> refSequenceOptional = refSequenceRepository.findById(user.getLocCode().trim());

	                    if (refSequenceOptional.isEmpty()) {

	                        logger.warn("Location code '{}' is not implemented in the ref_sequence table", user.getLocCode());

	                        return "Application not implemented for the location";

	                    }



	                    return jwt;

	                } else {

	                    logger.warn("OTP expired for user ID: {}", user.getUserId());

	                    return "OTP expired";

	                }

	            } else {

	                logger.warn("Invalid OTP '{}' provided for user ID: {}", otp, user.getUserId());

	                return "Invalid OTP";

	            }

	        } else {

	            logger.warn("User with mobile number {} not found", mobileNumber);

	            return "User not found";

	        }

	    }
	 
	 public ResponseEntity<ProfileResponse> getProfile() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String userId = authentication.getName();  // Assuming userId is the username

	        Optional<MstUser> userOptional = mstUserRepository.findByUserId(userId);

	        if (userOptional.isPresent()) {
	            MstUser user = userOptional.get();
	            ProfileResponse profile = new ProfileResponse();
	            profile.setName(user.getUserName());
	            profile.setDesignation("Dispatch"); // Assuming dispatch designation is "Dispatch"
	            profile.setLocName(user.getLocCode());

	            return ResponseEntity.ok(profile);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
    
	
}
