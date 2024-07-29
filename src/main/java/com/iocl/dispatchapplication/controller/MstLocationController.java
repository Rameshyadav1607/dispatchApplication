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
import org.springframework.web.bind.annotation.RestController;

import com.iocl.dispatchapplication.model.MstLocation;
import com.iocl.dispatchapplication.service.MstLocationService;



@RestController
@RequestMapping("/api/v1/location")
public class MstLocationController {
    @Autowired
	private MstLocationService service;
    @GetMapping("/all")
    public ResponseEntity<List<MstLocation>> getAllLocations(){
		return service.getAllLocations();
    	
    }
    @GetMapping("/{locCode}")
    public ResponseEntity<MstLocation> getLocationByLocCode(@PathVariable String locCode){
		return service.getLocationByLocCode(locCode);
    	
    }
    @PostMapping("/save")
    public ResponseEntity<MstLocation> createLocation(@RequestBody MstLocation mstLocation){
		return service.createLocation(mstLocation);
    	
    }
    @PutMapping("/{locCode}")
    public ResponseEntity<MstLocation> updateLocation(@PathVariable String locCode,@RequestBody MstLocation mstLocation){
		return service.updateLocation(locCode,mstLocation);
    	
    }
    @DeleteMapping("/delete/{locCode}")
    public ResponseEntity<Void> deleteLocationId(@PathVariable String locCode){
		return service.deleteLocationById(locCode);
    	
    }

}
