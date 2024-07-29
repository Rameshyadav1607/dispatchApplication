package com.iocl.dispatchapplication.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iocl.dispatchapplication.repository.TrnParcelInRepository;
import com.iocl.dispatchapplication.repository.TrnParcelOutRepository;



@Service
public class DashboardService {
	
	private TrnParcelInRepository trnParcelInRepository;
	private TrnParcelOutRepository trnParcelOutRepository;
	
	@Autowired
	public DashboardService(TrnParcelInRepository trnParcelInRepository,
			TrnParcelOutRepository trnParcelOutRepository) {
		this.trnParcelInRepository = trnParcelInRepository;
		this.trnParcelOutRepository = trnParcelOutRepository;
	}
	
	public Long getTodayParcelInCount() {
		LocalDate today=LocalDate.now();
		             
		return  trnParcelInRepository.countByCreatedDate(today);
		
	}
	public Long getTodayParcelOutCount() {
		LocalDate today=LocalDate.now();
		
		return trnParcelOutRepository.countByCreatedDate(today);
		
	}
	
	public Long getTotalTodayRecords() {
		return getTodayParcelInCount()+getTodayParcelOutCount();
		
	}
	
	 public List<?> getRecordsBetweenDates(LocalDate startDate, LocalDate endDate, String type) {
	        if (type.equalsIgnoreCase("In")) {
	            return trnParcelInRepository.findByCreatedDateBetweenOrderByCreatedDateDesc(startDate, endDate);
	        } else if (type.equalsIgnoreCase("Out")) {
	            return trnParcelOutRepository.findByCreatedDateBetweenOrderByCreatedDateDesc(startDate, endDate);
	        } else {
	            throw new IllegalArgumentException("Invalid type: " + type);
	        }
	    }

}
