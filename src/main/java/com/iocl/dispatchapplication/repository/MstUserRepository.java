package com.iocl.dispatchapplication.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.iocl.dispatchapplication.model.MstUser;
import com.iocl.dispatchapplication.model.MstUserPK;

public interface MstUserRepository extends JpaRepository<MstUser,MstUserPK>{

	Optional<MstUser> findByMobileNumber(Long mobileNumber);
	//Optional<MstUser> findByMobileNumber(Long mobileNumber);

	Optional<MstUser> findByUserId(String userId);

	List<MstUser> findByLocCode(String locCode);
}
