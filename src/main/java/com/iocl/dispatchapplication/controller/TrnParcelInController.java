package com.iocl.dispatchapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iocl.dispatchapplication.exception.DuplicateConsignmentNumberException;
import com.iocl.dispatchapplication.model.TrnParcelInId;
import com.iocl.dispatchapplication.requestdto.TrnParcelInDTO;
import com.iocl.dispatchapplication.responsedto.TrnParcelInResponseDTO;
import com.iocl.dispatchapplication.service.TrnParcelInService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/parcels/in")
public class TrnParcelInController {
	@Autowired
	private TrnParcelInService trnParcelInService;
	 @PostMapping("/save")
	    public ResponseEntity<?> saveParcelIn(@RequestBody TrnParcelInDTO trnParcelInDTO,HttpServletRequest httpRequest) {
			return trnParcelInService.saveParcelIn(trnParcelInDTO,httpRequest);
//	        try {
//	            TrnParcelInResponseDTO trnParcelInResponseDTO = trnParcelInService.saveParcelIn(trnParcelInDTO,httpRequest);
//	            return new ResponseEntity<>(trnParcelInResponseDTO, HttpStatus.CREATED);
//	        } catch (DuplicateConsignmentNumberException ex) {
//	            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
//	        }
	    }
	
	  @GetMapping("/all")
	    public ResponseEntity<List<TrnParcelInResponseDTO>> getAllParcels() {
	        List<TrnParcelInResponseDTO> parcels = trnParcelInService.getAllParcels();
	        return new ResponseEntity<>(parcels, HttpStatus.OK);
	    }

	  @GetMapping("/{recipientLocCode}/{inTrackingId}")
	    public ResponseEntity<TrnParcelInResponseDTO> getParcelById(@PathVariable String recipientLocCode, @PathVariable Long inTrackingId) {
	        TrnParcelInId id = new TrnParcelInId(recipientLocCode, inTrackingId);
	        TrnParcelInResponseDTO parcel = trnParcelInService.getParcelById(id);
	        return parcel != null ? new ResponseEntity<>(parcel, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  @PutMapping("/{recipientLocCode}/{inTrackingId}")
	    public ResponseEntity<TrnParcelInResponseDTO> updateParcel(@PathVariable String recipientLocCode, @PathVariable Long inTrackingId, @RequestBody TrnParcelInDTO trnParcelInDTO) {
	        TrnParcelInId id = new TrnParcelInId(recipientLocCode, inTrackingId);
	        TrnParcelInResponseDTO updatedParcel = trnParcelInService.updateParcel(id, trnParcelInDTO);
	        return updatedParcel != null ? new ResponseEntity<>(updatedParcel, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  @DeleteMapping("/{recipientLocCode}/{inTrackingId}")
	    public ResponseEntity<Void> deleteParcel(@PathVariable String recipientLocCode, @PathVariable Long inTrackingId) {
	        TrnParcelInId id = new TrnParcelInId(recipientLocCode, inTrackingId);
	        trnParcelInService.deleteParcel(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	

}
