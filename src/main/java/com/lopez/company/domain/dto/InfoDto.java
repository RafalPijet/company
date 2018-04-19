package com.lopez.company.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InfoDto {
    private long id;
    private String firstname;
    private String lastname;
    private BigDecimal pesel;
    private String sex;
    private long pid;
    private String pname;
    private long rid;
    private String name;
    private String description;
    private String date;
    private double value;
}
