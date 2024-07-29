package com.iocl.dispatchapplication.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iocl.dispatchapplication.requestdto.DateRangeQueryDTO;
import com.iocl.dispatchapplication.responsedto.DashboardResponseDTO;
import com.iocl.dispatchapplication.service.DashboardService;


@RestController
@RequestMapping("/api/v1/Dashboard")
public class DashboardController {
	
	@Autowired
	private DashboardService service;
	
	@GetMapping("/todayPacelCount")
	public DashboardResponseDTO getDashboard() {
        long totalIn = service.getTodayParcelInCount();
        long totalOut = service.getTodayParcelOutCount();
        long total = service.getTotalTodayRecords();
        return new DashboardResponseDTO(total,totalIn,totalOut);
    }
	
	 @GetMapping("/records")
	    public ResponseEntity<List<?>> getRecordsBetweenDates(@RequestParam("startDate") String startDateStr,
	                                          @RequestParam("endDate") String endDateStr,
	                                          @RequestParam("type") String type) {
	        LocalDate startDate = LocalDate.parse(startDateStr);
	        LocalDate endDate = LocalDate.parse(endDateStr);
	        
	        List<?> records = service.getRecordsBetweenDates(startDate, endDate, type);
	        
	        if (records.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	        
	        return ResponseEntity.ok(records);
	    }
	 
	 @PostMapping("/records")
	 public List<?> getRecordsBetweenDates(@RequestBody DateRangeQueryDTO dateRangeQuery) {
	     LocalDate startDate = dateRangeQuery.getStartDate();
	     LocalDate endDate = dateRangeQuery.getEndDate();
	     String type = dateRangeQuery.getType();
	     return service.getRecordsBetweenDates(startDate, endDate, type);
	 }
}
