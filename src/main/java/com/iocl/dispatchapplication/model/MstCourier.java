package com.iocl.dispatchapplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="MST_COURIER")
public class MstCourier {
	@Id
	@Column(name="COURIER_CODE")
	private String courierCode;
	@Column(name="COURIER_NAME")
    private String courierName;

}
