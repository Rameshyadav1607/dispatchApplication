package com.iocl.dispatchapplication.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iocl.dispatchapplication.exception.DuplicateConsignmentNumberException;
import com.iocl.dispatchapplication.jwt.JwtUtils;
import com.iocl.dispatchapplication.model.TrnParcelIn;
import com.iocl.dispatchapplication.model.TrnParcelInId;
import com.iocl.dispatchapplication.repository.TrnParcelInRepository;
import com.iocl.dispatchapplication.requestdto.TrnParcelInDTO;
import com.iocl.dispatchapplication.responsedto.TrnParcelInResponseDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TrnParcelInService {
	@Autowired
	private TrnParcelInRepository trnParcelInRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<?> saveParcelIn(TrnParcelInDTO trnParcelInDTO,HttpServletRequest httpRequest) {
    	try {
    		 String token = jwtUtils.getJwtFromCookies(httpRequest);
	           // Validate and extract information from the JWT token
		        String locCode = jwtUtils.extractLocCode(token);
		        String username = jwtUtils.extractUserId(token);
		        if (locCode == null || username == null) {
		            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
		        }
		        if (trnParcelInRepository.existsByConsignmentNumber(trnParcelInDTO.getConsignmentNumber())) {
	                throw new DuplicateConsignmentNumberException("Consignment number already exists");
	            }
		        TrnParcelIn trnParcel = modelMapper.map(trnParcelInDTO, TrnParcelIn.class);
	    		 Long nextId = trnParcelInRepository.fetchNextId();
	    		 trnParcel.setInTrackingId(nextId);
	    		 trnParcel.setCreatedBy(username);
	    		 trnParcel.setCreatedDate(LocalDate.now());
	    		 trnParcel.setRecipientLocCode(locCode);
	           TrnParcelIn savedTrnParcelIn = trnParcelInRepository.save(trnParcel);
	           return new ResponseEntity<>(HttpStatus.CREATED);
    	} catch (DuplicateConsignmentNumberException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
//    	           String token = jwtUtils.getJwtFromCookies(httpRequest);
//    	           // Validate and extract information from the JWT token
//    		        String locCode = jwtUtils.extractLocCode(token);
//    		        String username = jwtUtils.extractUserId(token);
//
//    		        if (locCode == null || username == null) {
//    		            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
//    		        }
//    		     // Check for duplicate consignment number
//    		        if (trnParcelInRepository.existsByConsignmentNumber(trnParcelInDTO.getConsignmentNumber())) {
//    		            return ResponseEntity.status(HttpStatus.CONFLICT).body("Consignment number already exists");
//    		        }
//    	try{
//    		 TrnParcelIn trnParcel = modelMapper.map(trnParcelInDTO, TrnParcelIn.class);
//    		 Long nextId = trnParcelInRepository.fetchNextId();
//    		 trnParcel.setInTrackingId(nextId);
//    		 trnParcel.setCreatedBy(username);
//    		 trnParcel.setCreatedDate(LocalDate.now());
//    		 trnParcel.setRecipientLocCode(locCode);
//           TrnParcelIn savedTrnParcelIn = trnParcelInRepository.save(trnParcel);
//           
//           return new ResponseEntity<>(HttpStatus.CREATED);
//          
//            
//    	}
//    	catch (Exception e) {
//			// TODO: handle exception
//    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//    		        
		
    }
	 public List<TrnParcelInResponseDTO> getAllParcels() {
	        List<TrnParcelIn> parcels = trnParcelInRepository.findAllByOrderByInTrackingIdDesc();
	        return parcels.stream()
	                      .map(parcel -> modelMapper.map(parcel, TrnParcelInResponseDTO.class))
	                      .toList();
	    }
	 public TrnParcelInResponseDTO getParcelById(TrnParcelInId id) {
	        Optional<TrnParcelIn> parcelOpt = trnParcelInRepository.findById(id);
	        return parcelOpt.map(parcel -> modelMapper.map(parcel, TrnParcelInResponseDTO.class))
	                        .orElse(null);
	    }


	 public TrnParcelInResponseDTO updateParcel(TrnParcelInId id, TrnParcelInDTO trnParcelInDTO) {
	        Optional<TrnParcelIn> parcelOpt = trnParcelInRepository.findById(id);
	        if (parcelOpt.isPresent()) {
	            TrnParcelIn trnParcel = parcelOpt.get();
	            modelMapper.map(trnParcelInDTO, trnParcel);// Update existing parcel with new values
	            trnParcel.setRecordStatus("A");
	            trnParcel.setLastUpdatedDate(LocalDateTime.now()); // Update the last updated date
	            TrnParcelIn updatedParcel = trnParcelInRepository.save(trnParcel);
	            return modelMapper.map(updatedParcel, TrnParcelInResponseDTO.class);
	        }
	        return null;
	    }
	 public void deleteParcel(TrnParcelInId id) {
	        Optional<TrnParcelIn> parcelOpt = trnParcelInRepository.findById(id);
	        if (parcelOpt.isPresent()) {
	            TrnParcelIn trnParcel = parcelOpt.get();
	            trnParcel.setRecordStatus("D");
	            trnParcelInRepository.save(trnParcel);
	        }
	    }
	
}
