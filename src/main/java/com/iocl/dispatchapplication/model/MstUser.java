package com.iocl.dispatchapplication.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mst_user")
@Setter
@Getter
@IdClass(MstUserPK.class)
public class MstUser {

	@Id
    @Column(name = "loc_code", length = 6)
    private String locCode;

    @Id
    @Column(name = "user_id", length = 10)
    private String userId;

    @Column(name = "user_name", length = 50)
    private String userName; 

    @Column(name="mobile_number",nullable = false)
    private Long mobileNumber;

    @Column(name = "role_id", length = 10)
    private String roleId;
    

    @Column(name = "status", length = 1)
    private String status = "A";
    

    @Column(name = "created_by", length = 10)
    private String createdBy;

    @Column(name = "created_date")
    private LocalDate createdDate=LocalDate.now();
    
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;
    
//    
//    @ManyToMany
//    @JoinTable(
//        name = "user_roles",
//        joinColumns = {
//            @JoinColumn(name = "loc_code", referencedColumnName = "loc_code"),
//            @JoinColumn(name = "user_id", referencedColumnName = "user_id")
//        },
//        inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private Set<MstRole> roles = new HashSet<>();
////    @ManyToOne
////    @JoinColumn(name = "loc_code", insertable = false, updatable = false)
////    private MstLocation mstLocation;
////
////    @ManyToOne
////    @JoinColumn(name = "role_id", insertable = false, updatable = false)
////    private MstRole mstRole;
////     
    
}
