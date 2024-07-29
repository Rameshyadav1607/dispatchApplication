package com.iocl.dispatchapplication.responsedto;

import lombok.Data;

@Data
public class DashboardResponseDTO {
	
	private long total;
    private long totalIn;
    private long totalOut;

	public DashboardResponseDTO(long total, long totalIn, long totalOut) {
		super();
		this.total = total;
		this.totalIn = totalIn;
		this.totalOut = totalOut;
	}

}
