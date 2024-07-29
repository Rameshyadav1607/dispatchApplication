package com.iocl.dispatchapplication.responsedto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TrnParcelInResponseDTO {
	    private String recipientLocCode;
	    private Long inTrackingId;
	    private String consignmentNumber;
	    private LocalDate consignmentDate;
	    private LocalDate receivedDate;
	    private String senderLocCode;
	    private String senderDepartment;
	    private String senderName;
	    private String recipientDepartment;
	    private String recipientName;
	    private String courierCode;
	    private String recordStatus;
	    private String createdBy;
	    private LocalDate createdDate;
	    private LocalDateTime lastUpdatedDate;


}
