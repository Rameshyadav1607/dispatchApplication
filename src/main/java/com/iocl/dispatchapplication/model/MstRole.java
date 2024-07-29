package com.iocl.dispatchapplication.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "mst_role")
public class MstRole {

    @Id
    @Column(name = "role_id", length = 10, nullable = false)
    private String roleId;

    @Column(name = "role_desc", length = 50, nullable = false)
    private String roleDesc;
}

