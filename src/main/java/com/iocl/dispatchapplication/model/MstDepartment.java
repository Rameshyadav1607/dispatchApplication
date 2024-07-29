package com.iocl.dispatchapplication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mst_department")
@Setter
@Getter
@IdClass(MstDepartmentPK.class)

public class MstDepartment {

	    @Id
	    @Column(name = "loc_code", length = 6)
	    private String locCode;

	    @Id
	    @Column(name = "dept_code", length = 6)
	    private String deptCode;

	    @Column(name = "dept_name", length = 40)
	    private String deptName;

	    // Relationships
	    @ManyToOne
	    @JoinColumn(name = "loc_code", insertable = false, updatable = false)
	    private MstLocation mstLocation;

}
