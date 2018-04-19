package com.lopez.company.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public RemunerationDto(String date, String name, String description, Double value) {
        this.date = date;
        this.name = name;
        this.description = description;
        this.value = value;
    }
}
