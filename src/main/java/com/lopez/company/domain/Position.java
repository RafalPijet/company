package com.lopez.company.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(
                name = "Position.getAllRecords",
                query = "FROM Position"
        ),
        @NamedQuery(
                name = "Position.getEmployeId",
                query = "FROM Position WHERE employees_id = :EMPLOYEES_ID"
        ),
        @NamedQuery(
                name = "Position.getPositionName",
                query = "FROM Position WHERE name = :NAME"
        )
})

@Entity
@Table(name = "POSITION")
public class Position {
    private long id;
    private String name;
    private Employe employe;

    public Position() {
    }
    public Position(String name) {
        this.name = name;
    }
    public Position(long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    public long getId() {
        return id;
    }
    private void setId(long id) {
        this.id = id;
    }
    @NotNull
    @Column(name = "NAME")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
