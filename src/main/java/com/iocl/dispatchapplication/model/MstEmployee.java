package com.iocl.dispatchapplication.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "emp_db")
public class MstEmployee {

    @Id
    @Column(name = "emp_code", nullable = false)
    private Long empCode;

    @Column(name = "emp_ini", length = 6)
    private String empIni;

    @Column(name = "emp_name", length = 60)
    private String empName;

    @Column(name = "designation", length = 50)
    private String designation;

    @Column(name = "curr_comp_code", length = 6)
    private String currCompCode;

    @Column(name = "curr_comp", length = 50)
    private String currComp;

    @Column(name = "pa_code", length = 6)
    private String paCode;

    @Column(name = "pa", length = 30)
    private String pa;

    @Column(name = "psa_code", length = 6)
    private String psaCode;

    @Column(name = "psa", length = 30)
    private String psa;

    @Column(name = "loc_code", length = 6)
    private String locCode;

    @Column(name = "loc_name", length = 50)
    private String locName;

    @Column(name = "email_id", length = 40)
    private String emailId;

    @Column(name = "emp_status_code", length = 1)
    private String empStatusCode;

    @Column(name = "emp_status", length = 20)
    private String empStatus;

    // Getters and setters
    public Long getEmpCode() {
        return empCode;
    }

    public void setEmpCode(Long empCode) {
        this.empCode = empCode;
    }

    public String getEmpIni() {
        return empIni;
    }

    public void setEmpIni(String empIni) {
        this.empIni = empIni;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCurrCompCode() {
        return currCompCode;
    }

    public void setCurrCompCode(String currCompCode) {
        this.currCompCode = currCompCode;
    }

    public String getCurrComp() {
        return currComp;
    }

    public void setCurrComp(String currComp) {
        this.currComp = currComp;
    }

    public String getPaCode() {
        return paCode;
    }

    public void setPaCode(String paCode) {
        this.paCode = paCode;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getPsaCode() {
        return psaCode;
    }

    public void setPsaCode(String psaCode) {
        this.psaCode = psaCode;
    }

    public String getPsa() {
        return psa;
    }

    public void setPsa(String psa) {
        this.psa = psa;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmpStatusCode() {
        return empStatusCode;
    }

    public void setEmpStatusCode(String empStatusCode) {
        this.empStatusCode = empStatusCode;
    }

    public String getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }
}
