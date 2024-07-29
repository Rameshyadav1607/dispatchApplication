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
@Table(name = "trn_parcel_out", schema = "public",uniqueConstraints = {
	    @UniqueConstraint(columnNames = "consignment_number")
	})
@IdClass(TrnParcelOutId.class)
public class TrnParcelOut {

    @Id
    @Column(name = "sender_loc_code", length = 6, nullable = false)
    private String senderLocCode;

    @Id
 //   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "out_tracking_id", nullable = false)
    private Long outTrackingId;

    @Column(name = "consignment_number", length = 30)
    private String consignmentNumber;

    @Column(name = "consignment_date")
    private LocalDate consignmentDate;

    @Column(name = "sender_department", length = 50)
    private String senderDepartment;

    @Column(name = "sender_name", length = 50)
    private String senderName;

    @Column(name = "recipient_loc_code", length = 6)
    private String recipientLocCode;

    @Column(name = "recipient_department", length = 50)
    private String recipientDepartment;

    @Column(name = "recipient_name", length = 50)
    private String recipientName;

    @Column(name = "courier_name", length = 10, nullable = false)
    private String courierName;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "unit_id", length = 10, nullable = false)
    private String unit;

    @Column(name = "record_status", length = 1)
    private String recordStatus = "A";

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDate createdDate= LocalDate.now();

    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

   
}
