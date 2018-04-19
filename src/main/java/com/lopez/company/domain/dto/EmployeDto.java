package com.lopez.company.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<PositionDto> positionDtoList = new ArrayList<>();
    private List<RemunerationDto> remunerationDtoList = new ArrayList<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeDto that = (EmployeDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(pesel, that.pesel) &&
                Objects.equals(sex, that.sex);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, pesel, sex);
    }
}
