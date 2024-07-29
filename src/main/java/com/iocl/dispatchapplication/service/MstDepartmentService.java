package com.iocl.dispatchapplication.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iocl.dispatchapplication.model.MstDepartment;
import com.iocl.dispatchapplication.model.MstDepartmentPK;
import com.iocl.dispatchapplication.repository.MstDepartmentRepository;
import com.iocl.dispatchapplication.responsedto.MstDepartmentDTO;


@Service
public class MstDepartmentService {
	@Autowired
	private MstDepartmentRepository repository;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<List<MstDepartment>> getDepartmentsByLocCode(String locCode){
		       List<MstDepartment>  locationCode=repository.findByLocCode(locCode);
		    if(locationCode.isEmpty())  
		    {
		    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
		    else {
		    	                          
		    	return new ResponseEntity<List<MstDepartment>>(locationCode,HttpStatus.OK);
		    }
		         
		    
	}

	public ResponseEntity<MstDepartmentDTO> saveDepartment(MstDepartmentDTO mstDepartmentDTO) {
		    //coverting dto to entity
		      ModelMapper mapper=new ModelMapper();
		                      
	      MstDepartment    mstDepartmentDTOs=mapper.map(mstDepartmentDTO,MstDepartment.class); 
	      //saving entity to database
	            MstDepartment departmentSaved=repository.save(mstDepartmentDTOs);
	         //cnverting entity to dto
	          MstDepartmentDTO   dto=mapper.map(departmentSaved,MstDepartmentDTO.class);
		
		return new ResponseEntity<MstDepartmentDTO>(dto,HttpStatus.CREATED);
	}

	
	  public ResponseEntity<List<MstDepartmentDTO>> getAllDepartment() {
	        List<MstDepartment> departments = repository.findAll();
	        List<MstDepartmentDTO> departmentDTOs = departments.stream()
	                .map(department -> modelMapper.map(department, MstDepartmentDTO.class))
	                .collect(Collectors.toList());
	        return new ResponseEntity<>(departmentDTOs, HttpStatus.OK);
	    }

	  public ResponseEntity<MstDepartmentDTO> updateDepartmentByDeptCode(String locCode, String deptCode, MstDepartmentDTO departmentDTO) {
	        MstDepartmentPK id = new MstDepartmentPK();
	        id.setLocCode(locCode);
	        id.setDeptCode(deptCode);
	        Optional<MstDepartment> existingDepartmentOpt = repository.findById(id);

	        if (!existingDepartmentOpt.isPresent()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        MstDepartment existingDepartment = existingDepartmentOpt.get();

	        // Manually update fields
	        existingDepartment.setDeptName(departmentDTO.getDeptName());

	        // Save updated department
	         repository.save(existingDepartment);

	        // Prepare response
	        MstDepartmentDTO responseDTO = modelMapper.map(existingDepartment, MstDepartmentDTO.class);
	        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	    }

	  public ResponseEntity<Void> deleteDepartmentByDeptCode(String locCode, String deptCode) {
	        MstDepartmentPK id = new MstDepartmentPK();
	        id.setLocCode(locCode);
	        id.setDeptCode(deptCode);
	        Optional<MstDepartment> existingDepartmentOpt = repository.findById(id);

	        if (!existingDepartmentOpt.isPresent()) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        MstDepartment department = existingDepartmentOpt.get();

	        repository.delete(department);

	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	
	


}
