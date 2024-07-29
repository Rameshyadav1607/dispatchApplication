package com.iocl.dispatchapplication.responsedto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TrnParcelOutResponseDTO {
	
	    private String senderLocCode;
	    private Long outTrackingId;
	    private String consignmentNumber;
	    private LocalDate consignmentDate;
	    private String senderDepartment;
	    private String senderName;
	    private String recipientLocCode;
	    private String recipientDepartment;
	    private String recipientName;
	    private String courierName;
	    private Double weight;
	    private String unit;
	    private String recordStatus;
	    private String createdBy;
	    private LocalDate createdDate;
	    private LocalDateTime lastUpdatedDate;

}
