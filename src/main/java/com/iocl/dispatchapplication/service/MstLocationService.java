package com.iocl.dispatchapplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iocl.dispatchapplication.model.MstLocation;
import com.iocl.dispatchapplication.repository.MstLocationRepository;


@Service
public class MstLocationService {

    @Autowired
    private MstLocationRepository locationRepository;

	public ResponseEntity<List<MstLocation>> getAllLocations() {
		   List<MstLocation>    mstLocation=locationRepository.findAll();
		return new ResponseEntity<List<MstLocation>>(mstLocation,HttpStatus.OK);
	}

	public ResponseEntity<MstLocation> getLocationByLocCode(String locCode) {
		
	    Optional<MstLocation>     mstlocation=locationRepository.findById(locCode);
	    if(mstlocation.isPresent()) {
	    	return new ResponseEntity<>(mstlocation.get(),HttpStatus.OK);
	    }
	    else {
	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
		
	}

	public ResponseEntity<MstLocation> createLocation(MstLocation mstLocation) {
		// TODO Auto-generated method stub
		       MstLocation      saveLocation=locationRepository.save(mstLocation);
		    return new ResponseEntity<MstLocation>(saveLocation,HttpStatus.CREATED);
		
	}

	public ResponseEntity<MstLocation> updateLocation(String locCode, MstLocation mstLocation) {
		// TODO Auto-generated method stub
        Optional<MstLocation>          locCodeId=locationRepository.findById(locCode);
        if(locCodeId.isPresent()) {
//        	  MstLocation   location=locCodeId.get();
//        	  location.setLocAddress(mstLocation.getLocAddress());
//        	  location.setLocName(mstLocation.getLocName());
//        	  location.setLocPin(mstLocation.getLocPin());
//        	  location.setLocState(mstLocation.getLocState());
//        	  location.setRegionOfficeCode(mstLocation.getRegionOfficeCode());
//        	  location.setStateOfficeCode(mstLocation.getStateOfficeCode());
        
        	mstLocation.setLocCode(locCode);
              MstLocation     locationSaved=locationRepository.save(mstLocation);
        	             
        	return new ResponseEntity<MstLocation>(locationSaved,HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        	
	}

	public ResponseEntity<Void> deleteLocationById(String locCode) {
		
	   Optional<MstLocation>    locCodeId=locationRepository.findById(locCode);
	   if(locCodeId.isPresent()) {
		   locationRepository.deleteById(locCode);
		   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	   }
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}


  
}
