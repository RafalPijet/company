package com.lopez.company.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "REMUNERATION")
public class Remuneration {
    private Long id;
    private Date date;
    private String name;
    private String description;
    private Double value;
    private Employe employe;

    public Remuneration(Date date, String name, String description, Double value) {
        this.date = date;
        this.name = name;
        this.description = description;
        this.value = value;
    }
    public Remuneration() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID", unique = true)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @NotNull
    @Column(name = "DATE")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    @NotNull
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
    @NotNull
    @Column(name = "VALUE")
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
    @ManyToOne
    @JoinColumn(name = "EMPLOYEES_ID")
    public Employe getEmploye() {
        return employe;
    }
    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
}
