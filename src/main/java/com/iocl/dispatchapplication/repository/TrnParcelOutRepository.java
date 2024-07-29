package com.iocl.dispatchapplication.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iocl.dispatchapplication.model.TrnParcelOut;
import com.iocl.dispatchapplication.model.TrnParcelOutId;


@Repository
public interface TrnParcelOutRepository extends JpaRepository<TrnParcelOut, TrnParcelOutId> {
	@Query("select max(tpi.outTrackingId)+1 from TrnParcelOut tpi")
	Long fetchNextId();

	 List<TrnParcelOut> findAllByOrderByOutTrackingIdDesc();

	Long countByCreatedDate(LocalDate today);

	//List<?> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate);

	List<?> findByCreatedDateBetweenOrderByCreatedDateDesc(LocalDate startDate, LocalDate endDate);

	boolean existsByConsignmentNumber(String consignmentNumber);
}

