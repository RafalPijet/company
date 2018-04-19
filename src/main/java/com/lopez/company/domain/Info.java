package com.lopez.company.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@NamedNativeQuery(
        name = "Info.getAllInfo",
        query = "SELECT E.ID, E.FIRSTNAME, E.LASTNAME, E.PESEL, E.SEX, P.ID AS PID, P.NAME AS PNAME, R.ID AS RID, R.NAME, R.DESCRIPTION, R.DATE, R.VALUE" +
                " FROM EMPLOYEES E INNER JOIN POSITION P ON E.ID = P.EMPLOYEES_ID INNER JOIN REMUNERATION R ON E.ID = R.EMPLOYEES_ID",
        resultClass = Info.class
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "INFO")
public class Info implements Serializable {
    @Id
    @Column(name = "ID")
    private long id;
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Column(name = "LASTNAME")
    private String lastname;
    @Column(name = "PESEL")
    private BigDecimal pesel;
    @Column(name = "SEX")
    private String sex;
    @Column(name = "PID")
    private long pid;
    @Column(name = "PNAME")
    private String pname;
    @Column(name = "RID")
    private long rid;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "DATE")
    private Date date;
    @Column(name = "VALUE")
    private double value;

/*
    public Info() {
    }

    public Info(long id, String firstname, String lastname, BigDecimal pesel, String sex, long pid, String pname, long rid, String name, String description, Date date, double value) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pesel = pesel;
        this.sex = sex;
        this.pid = pid;
        this.pname = pname;
        this.rid = rid;
        this.name = name;
        this.description = description;
        this.date = date;
        this.value = value;
    }
    @Id
    @Column(name = "ID")
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "FIRSTNAME")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    @Column(name = "LASTNAME")
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    @Column(name = "PESEL")
    public BigDecimal getPesel() {
        return pesel;
    }

    public void setPesel(BigDecimal pesel) {
        this.pesel = pesel;
    }
    @Column(name = "SEX")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    @Column(name = "PID")
    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    @Column(name = "PNAME")
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
    @Column(name = "RID")
    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name = "DATE")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Column(name = "VALUE")
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    } */
}
