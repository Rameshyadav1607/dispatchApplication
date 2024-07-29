package com.iocl.dispatchapplication.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.iocl.dispatchapplication.model.OtpEntity;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity, UUID>{

//    Optional<OtpEntity> findByEmpCodeAndOtp(String empCode, int otp);
//    @Query("SELECT o FROM OtpEntity o WHERE o.empCode = :empCode ORDER BY o.createdAt DESC")
//    OtpEntity findlastbyid(@Param("empCode") String empCode);
//    
//    @Query("SELECT o FROM OtpEntity o WHERE o.empCode = :empCode ORDER BY o.createdAt DESC")
//    OtpEntity findLastByUserId(@Param("empCode") String empCode);
//	@Query("SELECT o FROM OtpEntity o WHERE o.empCode = :empCode ORDER BY o.createdAt DESC")
//    OtpEntity findLastByEmpCode(@Param("empCode") String empCode);
    OtpEntity findFirstByEmpCodeOrderByCreatedAtDesc(String empCode);


}
