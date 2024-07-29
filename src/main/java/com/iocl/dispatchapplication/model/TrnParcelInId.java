package com.iocl.dispatchapplication.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrnParcelInId implements Serializable {

   
	private String recipientLocCode;
    private Long inTrackingId;
}
