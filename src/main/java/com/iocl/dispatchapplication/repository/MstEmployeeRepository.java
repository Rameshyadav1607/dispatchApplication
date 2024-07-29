package com.iocl.dispatchapplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iocl.dispatchapplication.model.MstEmployee;

@Repository
public interface MstEmployeeRepository extends JpaRepository<MstEmployee,Long>{
  
	List<MstEmployee> findBylocCode(String loc);

	Optional<MstEmployee> findByEmpCode(Integer id);
   
	


}
