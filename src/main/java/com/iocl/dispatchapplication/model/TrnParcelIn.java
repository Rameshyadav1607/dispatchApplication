package com.iocl.dispatchapplication.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
@Data
@Entity
@Table(name = "trn_parcel_in", schema = "public", uniqueConstraints = {
	    @UniqueConstraint(columnNames = "consignment_number")
	})
@IdClass(TrnParcelInId.class)
public class TrnParcelIn {

    @Id
    @Column(name = "recipient_loc_code", length = 6, nullable = false)
    private String recipientLocCode;

    @Id
 //  @GeneratedValue(strategy = GenerationType.IDENTITY)//remove
    @Column(name = "in_tracking_id", nullable = false)
    private Long inTrackingId;

    @Column(name = "consignment_number", length = 30, nullable = false)
    private String consignmentNumber;

    @Column(name = "consignment_date", nullable = false)
    private LocalDate consignmentDate;

    @Column(name = "received_date", nullable = false)
    private LocalDate receivedDate;

    @Column(name = "sender_loc_code", length = 6)
    private String senderLocCode;

    @Column(name = "sender_department", length = 50)
    private String senderDepartment;

    @Column(name = "sender_name", length = 50)
    private String senderName;

    @Column(name = "recipient_department", length = 50)
    private String recipientDepartment;

    @Column(name = "recipient_name", length = 50)
    private String recipientName;

    @Column(name = "courier_name", length = 10, nullable = false)
    private String courierCode;

    @Column(name = "record_status", length = 1, nullable = false)
    private String recordStatus = "A";

    @Column(name = "created_by", length = 50, nullable = false)
    private String createdBy;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDate createdDate;

    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

  
}
