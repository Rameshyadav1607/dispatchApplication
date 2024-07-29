package com.iocl.dispatchapplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iocl.dispatchapplication.model.RefSequence;
import com.iocl.dispatchapplication.repository.RefSequenceRepository;

@Service
public class RefSequenceService {

	    @Autowired
	    private RefSequenceRepository refSequenceRepository;

	    public ResponseEntity<RefSequence> createRefSequence(RefSequence refSequence) {
	    	        RefSequence    RefSequencesaved=refSequenceRepository.save(refSequence);
	        return new ResponseEntity<RefSequence>(RefSequencesaved,HttpStatus.CREATED);
	    }
	    public List<RefSequence> getAllRefSequences() {
	        return refSequenceRepository.findAll();
	    }
	    public ResponseEntity<RefSequence> getRefSequenceByLocCode(String locCode) {
	        Optional<RefSequence> refSequence = refSequenceRepository.findById(locCode);
	        return refSequence.map(ResponseEntity::ok)
	                          .orElseGet(() -> ResponseEntity.notFound().build());
	    }
}
