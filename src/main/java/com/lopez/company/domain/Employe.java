package com.lopez.company.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EMPLOYEES")
public class Employe {
    private Long id;
    private String firstName;
    private String lastName;
    private Long pesel;
    private String sex;
    private List<Position> position = new ArrayList<>();
    private List<Remuneration> remuneration = new ArrayList<>();

    public Employe(String firstName, String lastName, Long pesel, String sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.sex = sex;
    }
    public Employe() {
    }
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @NotNull
    @Column(name = "FIRSTNAME")
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @NotNull
    @Column(name = "LASTNAME")
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @NotNull
    @Column(name = "PESEL", unique = true)
    public Long getPesel() {
        return pesel;
    }
    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }
    @Column(name = "SEX")
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    @OneToMany(targetEntity = Position.class,
                mappedBy = "employe",
                cascade = CascadeType.ALL,
                fetch = FetchType.EAGER)
    public List<Position> getPosition() {
        return position;
    }
    public void setPosition(List<Position> position) {
        this.position = position;
    }
    @OneToMany(targetEntity = Remuneration.class,
                mappedBy = "employe",
                cascade = CascadeType.ALL,
                fetch = FetchType.EAGER)
    public List<Remuneration> getRemuneration() {
        return remuneration;
    }
    public void setRemuneration(List<Remuneration> remuneration) {
        this.remuneration = remuneration;
    }
}
