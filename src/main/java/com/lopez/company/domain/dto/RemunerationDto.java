package com.lopez.company.domain.dto;

import com.lopez.company.domain.Employe;
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
    private Date date;
    private String name;
    private String description;
    private Double value;
    private Employe employe;

    public RemunerationDto(Date date, String name, String description, Double value) {
        this.date = date;
        this.name = name;
        this.description = description;
        this.value = value;
    }
    public RemunerationDto(Long id, Date date, String name, String description, Double value) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getValue() {
        return value;
    }

    public Employe getEmploye() {
        return employe;
    }
}
