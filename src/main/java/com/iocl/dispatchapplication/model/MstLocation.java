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
@Table(name = "mst_location")
public class MstLocation {

    @Id
    @Column(name = "loc_code", length = 6, nullable = false)
    private String locCode;

    @Column(name = "loc_name", length = 50, nullable = false)
    private String locName;

    @Column(name = "state_office_code", length = 6, nullable = false)
    private String stateOfficeCode;

    @Column(name = "region_office_code", length = 6, nullable = false)
    private String regionOfficeCode;

    @Column(name = "loc_address", length = 500)
    private String locAddress;

    @Column(name = "loc_state", length = 500)
    private String locState;

    @Column(name = "loc_pin", length = 10)
    private String locPin;

	
//    @OneToMany(mappedBy = "location")
//    private List<MstDepartment> departments;
//
//    @OneToMany(mappedBy = "location")
//    private List<MstUser> users;
    
}
