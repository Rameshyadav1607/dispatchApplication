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
@Table(name = "captcha")
public class CaptchaEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;
  
  @Column(name = "value", nullable = false)
  private String value;
  
  @Column(name = "expiry_time", nullable = false)
  private LocalDateTime expiryTime;
}
