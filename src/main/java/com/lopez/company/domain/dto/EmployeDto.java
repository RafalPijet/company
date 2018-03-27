package com.lopez.company.domain.dto;

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
}
