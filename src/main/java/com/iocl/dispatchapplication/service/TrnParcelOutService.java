package com.iocl.dispatchapplication.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iocl.dispatchapplication.exception.DuplicateConsignmentNumberException;
import com.iocl.dispatchapplication.jwt.JwtUtils;
import com.iocl.dispatchapplication.model.TrnParcelOut;
import com.iocl.dispatchapplication.model.TrnParcelOutId;
import com.iocl.dispatchapplication.repository.TrnParcelOutRepository;
import com.iocl.dispatchapplication.requestdto.TrnParcelOutDTO;
import com.iocl.dispatchapplication.responsedto.TrnParcelOutResponseDTO;

import jakarta.servlet.http.HttpServletRequest;



@Service
public class TrnParcelOutService {
    @Autowired
    private TrnParcelOutRepository trnParcelOutRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<?> saveParcelOut(TrnParcelOutDTO trnParcelOutDTO,HttpServletRequest httpServletRequest) {
    	                      String  token=jwtUtils.getJwtFromCookies(httpServletRequest);
    	                      String  locCode=jwtUtils.extractLocCode(token);
    	                      String username=jwtUtils.extractUserId(token);
    	                      // Check if consignment_number already exists
    	                      if (trnParcelOutRepository.existsByConsignmentNumber(trnParcelOutDTO.getConsignmentNumber())) {
    	                   throw new DuplicateConsignmentNumberException("Consignment number already exists");
    	                      }
    	  try {                    
    	                      
                TrnParcelOut trnParcelOut = modelMapper.map(trnParcelOutDTO, TrnParcelOut.class);
              trnParcelOut.setSenderLocCode(locCode);
              trnParcelOut.setCreatedBy(username);
              TrnParcelOut savedTrnParcelOut = trnParcelOutRepository.save(trnParcelOut);
              Long nextId = trnParcelOutRepository.fetchNextId();
              savedTrnParcelOut.setOutTrackingId(nextId);
              return new ResponseEntity<>(HttpStatus.CREATED);
        }
    	  catch (Exception e) {
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }

    public List<TrnParcelOutResponseDTO> getAllParcelsOutSortedByOutTrackingIdDesc() {
        List<TrnParcelOut> parcels = trnParcelOutRepository.findAllByOrderByOutTrackingIdDesc();
        return parcels.stream()
                      .map(parcel -> modelMapper.map(parcel, TrnParcelOutResponseDTO.class))
                      .collect(Collectors.toList());
    }

    public TrnParcelOutResponseDTO getParcelOutById(TrnParcelOutId id) {
        Optional<TrnParcelOut> parcelOpt = trnParcelOutRepository.findById(id);
        return parcelOpt.map(parcel -> modelMapper.map(parcel, TrnParcelOutResponseDTO.class))
                        .orElse(null);
    }

    public TrnParcelOutResponseDTO updateParcelOut(TrnParcelOutId id, TrnParcelOutDTO trnParcelOutDTO) {
        Optional<TrnParcelOut> parcelOpt = trnParcelOutRepository.findById(id);
        if (parcelOpt.isPresent()) {
            TrnParcelOut trnParcelOut = parcelOpt.get();
                 modelMapper.map(trnParcelOutDTO, trnParcelOut);
                 trnParcelOut.setRecordStatus("A");
            trnParcelOut.setLastUpdatedDate(LocalDateTime.now());
            TrnParcelOut updatedParcelOut = trnParcelOutRepository.save(trnParcelOut);
           
            return modelMapper.map(updatedParcelOut, TrnParcelOutResponseDTO.class);
        }
        return null;
    }

    public void deleteParcelOut(TrnParcelOutId id) {
        Optional<TrnParcelOut> parcelOpt = trnParcelOutRepository.findById(id);
        if (parcelOpt.isPresent()) {
            TrnParcelOut trnParcelOut = parcelOpt.get();
            trnParcelOut.setRecordStatus("D");
            trnParcelOutRepository.save(trnParcelOut);
        }
    }
}
