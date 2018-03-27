package com.lopez.company.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NamedQueries({
        @NamedQuery(
                name = "Remuneration.getAllRecords",
                query = "FROM Remuneration"
        ),
        @NamedQuery(
                name = "Remuneration.getEmployeId",
                query = "FROM Remuneration WHERE employees_id = :EMPLOYEES_ID"
        )
})

@Entity
@Table(name = "REMUNERATION")
public class Remuneration {
    private long id;
    private Date date;
    private String name;
    private String description;
    private Double value;
    private Employe employe;

    public Remuneration() {
    }
    public Remuneration(Date date, String name, String description, Double value) {
        this.date = date;
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public Remuneration(long id, Date date, String name, String description, Double value) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.description = description;
        this.value = value;
    }
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    public long getId() {
        return id;
    }
    public void setId(long id) {
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
