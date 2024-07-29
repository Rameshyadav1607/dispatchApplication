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

import com.iocl.dispatchapplication.requestdto.MstCourierDTO;
import com.iocl.dispatchapplication.service.MstCourierService;

;

@RestController
@RequestMapping("/api/v1/courier")
public class MstCourierController {
	
	private  MstCourierService service;
	
	@Autowired
	public MstCourierController(MstCourierService service) {
	
		this.service = service;
	}


	@PostMapping("/save")
	public ResponseEntity<MstCourierDTO> saveCourier(@RequestBody MstCourierDTO mstCourierDTO){
		                 MstCourierDTO    mstCourier=service.saveCourier(mstCourierDTO); 
		return new ResponseEntity<MstCourierDTO>(mstCourier, HttpStatus.CREATED);
		
	}
	    @GetMapping("/all")
	    public ResponseEntity<List<MstCourierDTO>> getAllCouriers() {
	        List<MstCourierDTO> couriers = service.getAllCouriers();
	        return ResponseEntity.ok(couriers);
	    }
	  
	  @PutMapping("/{courierCode}")
	    public ResponseEntity<MstCourierDTO> updateCourier(@PathVariable String courierCode, @RequestBody MstCourierDTO courierDTO) {
	        MstCourierDTO updatedCourier = service.updateCourier(courierCode, courierDTO);
	        return ResponseEntity.ok(updatedCourier);
	    }
	  
	  @DeleteMapping("/{courierCode}")
	    public ResponseEntity<Void> deleteCourier(@PathVariable String courierCode) {
	        service.deleteCourier(courierCode);
	        return ResponseEntity.noContent().build();
	    }

}
