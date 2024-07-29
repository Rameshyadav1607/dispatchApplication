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

import com.iocl.dispatchapplication.model.MstDepartment;
import com.iocl.dispatchapplication.responsedto.MstDepartmentDTO;
import com.iocl.dispatchapplication.service.MstDepartmentService;




@RestController
@RequestMapping("/api/v1/department")
public class MstDepartmentController {

	@Autowired
	private MstDepartmentService service;

	@GetMapping("/{locCode}")
	public ResponseEntity<List<MstDepartment>> getDepartmentsByLocCode(@PathVariable String locCode){
		return service.getDepartmentsByLocCode(locCode);

	}
	@PostMapping("/save")
	public ResponseEntity<MstDepartmentDTO> saveDepartment(@RequestBody MstDepartmentDTO mstDepartmentDTO){
		return service.saveDepartment(mstDepartmentDTO);
		
	}
	@GetMapping("/all")
	public ResponseEntity<List<MstDepartmentDTO>> getAllDepartments(){
		return service.getAllDepartment();
		
	}
	
	  // Update by Dept Code
    @PutMapping("/{locCode}/{deptCode}")
    public ResponseEntity<MstDepartmentDTO> updateDepartmentByDeptCode(@PathVariable String locCode, @PathVariable String deptCode, @RequestBody MstDepartmentDTO departmentDTO) {
        return service.updateDepartmentByDeptCode(locCode, deptCode, departmentDTO);
    }
    @DeleteMapping("/{locCode}/{deptCode}")
    public ResponseEntity<Void> deleteDepartmentByDeptCode(@PathVariable String locCode, @PathVariable String deptCode) {
        return service.deleteDepartmentByDeptCode(locCode, deptCode);
    }
}
