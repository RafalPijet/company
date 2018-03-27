package com.lopez.company.domain.dto;

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

    public PositionDto(String name) {
        this.name = name;
    }
}
