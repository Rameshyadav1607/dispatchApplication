package com.iocl.dispatchapplication.requestdto;


import java.time.LocalDate;

import lombok.Data;
@Data
public class TrnParcelOutDTO {

   // private String senderLocCode;
   // private Long outTrackingId;
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
  //  private String recordStatus;
  //  private String createdBy;
  //  private LocalDate createdDate;

    
}
