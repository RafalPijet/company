package com.lopez.company.domain.dto;

import com.lopez.company.domain.Employe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PositionDto {
    private Long id;
    private String name;
    private Employe employe;

    public PositionDto(String name) {
        this.name = name;
    }

    public PositionDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Employe getEmploye() {
        return employe;
    }
}
