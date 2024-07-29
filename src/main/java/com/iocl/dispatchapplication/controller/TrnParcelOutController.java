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
import com.iocl.dispatchapplication.model.TrnParcelOutId;
import com.iocl.dispatchapplication.requestdto.TrnParcelOutDTO;
import com.iocl.dispatchapplication.responsedto.TrnParcelOutResponseDTO;
import com.iocl.dispatchapplication.service.TrnParcelOutService;

import jakarta.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/api/parcels/out")
public class TrnParcelOutController {
    @Autowired
    private TrnParcelOutService trnParcelOutService;

    @PostMapping("/save")
    public ResponseEntity<?> saveParcelIn(@RequestBody TrnParcelOutDTO trnParceloutDTO,HttpServletRequest httpServletRequest) {
           return trnParcelOutService.saveParcelOut(trnParceloutDTO, httpServletRequest);
       
    }
    @GetMapping("/all")
    public ResponseEntity<List<TrnParcelOutResponseDTO>> getAllParcelsOut() {
        List<TrnParcelOutResponseDTO> parcels = trnParcelOutService.getAllParcelsOutSortedByOutTrackingIdDesc();
        return new ResponseEntity<>(parcels, HttpStatus.OK);
    }

    @GetMapping("/{senderLocCode}/{outTrackingId}")
    public ResponseEntity<TrnParcelOutResponseDTO> getParcelOutById(@PathVariable String senderLocCode, @PathVariable Long outTrackingId) {
        TrnParcelOutId id = new TrnParcelOutId(senderLocCode, outTrackingId);
        TrnParcelOutResponseDTO parcel = trnParcelOutService.getParcelOutById(id);
        return parcel != null ? new ResponseEntity<>(parcel, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{senderLocCode}/{outTrackingId}")
    public ResponseEntity<TrnParcelOutResponseDTO> updateParcelOut(@PathVariable String senderLocCode, @PathVariable Long outTrackingId, @RequestBody TrnParcelOutDTO trnParcelOutDTO) {
        TrnParcelOutId id = new TrnParcelOutId(senderLocCode, outTrackingId);
        TrnParcelOutResponseDTO updatedParcelOut = trnParcelOutService.updateParcelOut(id, trnParcelOutDTO);
        return updatedParcelOut != null ? new ResponseEntity<>(updatedParcelOut, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{senderLocCode}/{outTrackingId}")
    public ResponseEntity<Void> deleteParcelOut(@PathVariable String senderLocCode, @PathVariable Long outTrackingId) {
        TrnParcelOutId id = new TrnParcelOutId(senderLocCode, outTrackingId);
        trnParcelOutService.deleteParcelOut(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
