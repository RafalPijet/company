package com.lopez.company.domain.dto;

import com.lopez.company.domain.Position;
import com.lopez.company.domain.Remuneration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal pesel;
    private String sex;
    private List<Position> position = new ArrayList<>();
    private List<Remuneration> remuneration = new ArrayList<>();

    public EmployeDto(String firstName, String lastName, BigDecimal pesel, String sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.sex = sex;
    }
    public EmployeDto(Long id, String firstName, String lastName, BigDecimal pesel, String sex) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getPesel() {
        return pesel;
    }

    public String getSex() {
        return sex;
    }

    public List<Position> getPosition() {
        return position;
    }

    public List<Remuneration> getRemuneration() {
        return remuneration;
    }
}
