package com.iocl.dispatchapplication.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "otp")
public class OtpEntity {

	  @Id
//	  @GeneratedValue(generator = "uuid")
//	    @GenericGenerator(name = "uuid", strategy = "uuid2")
	  @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name="id")
	    private UUID id;
        @Column(name="emp_code")
	    private String empCode; // Assuming this is the employee code or identifier
        @Column(name="otp")
	    private int otp;
        @Column(name="created_at")
	    private LocalDateTime createdAt;
        @Column(name="expiration_at")
	    private LocalDateTime expirationAt;
}
