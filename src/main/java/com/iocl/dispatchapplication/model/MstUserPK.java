package com.iocl.dispatchapplication.model;
import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MstUserPK implements Serializable{


	    private String locCode;
	    private String userId;

	    // Default constructor
	    public MstUserPK() {
	    }

	    // Constructor
	    public MstUserPK(String locCode, String userId) {
	        this.locCode = locCode;
	        this.userId = userId;
	    }

}