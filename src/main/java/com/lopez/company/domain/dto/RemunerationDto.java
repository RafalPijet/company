package com.lopez.company.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RemunerationDto {
    private Long id;
    private String date;
    private String name;
    private String description;
    private Double value;

    public RemunerationDto(Date date, String name, String description, Double value) {
        this.date = dateUtility(date);
        this.name = name;
        this.description = description;
        this.value = value;
    }
    private String dateUtility(Date date) {
        return date.toString().substring(0, 10);
    }
}
