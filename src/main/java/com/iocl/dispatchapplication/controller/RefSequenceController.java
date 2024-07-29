package com.iocl.dispatchapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iocl.dispatchapplication.model.RefSequence;
import com.iocl.dispatchapplication.service.RefSequenceService;

@RestController
@RequestMapping("/api/ref-sequences")
public class RefSequenceController {
	    @Autowired
	    private RefSequenceService refSequenceService;

	    @PostMapping
	    public ResponseEntity<RefSequence> createRefSequence(@RequestBody RefSequence refSequence) {
	        return refSequenceService.createRefSequence(refSequence);
	    }

	    @GetMapping
	    public ResponseEntity<List<RefSequence>> getAllRefSequences() {
	        return ResponseEntity.ok(refSequenceService.getAllRefSequences());
	    }
	    @GetMapping("/{locCode}") // New endpoint to get RefSequence by locCode
	    public ResponseEntity<RefSequence> getRefSequenceByLocCode(@PathVariable String locCode) {
	        return refSequenceService.getRefSequenceByLocCode(locCode);
	    }

}
