package com.iocl.dispatchapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iocl.dispatchapplication.model.MstDepartment;
import com.iocl.dispatchapplication.model.MstDepartmentPK;


@Repository
public interface MstDepartmentRepository extends JpaRepository<MstDepartment,MstDepartmentPK> {

	MstDepartment findByDeptCode(String deptCode);

	List<MstDepartment> findByLocCode(String locCode);

	

}
