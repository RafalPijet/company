package com.lopez.company.mapper;

import com.lopez.company.domain.Remuneration;
import com.lopez.company.domain.dto.RemunerationDto;
import org.springframework.stereotype.Component;

@Component
public class RemunerationMapper {
    public Remuneration mapToRemuneration(RemunerationDto remunerationDto) {
        return new Remuneration(remunerationDto.getDate(),remunerationDto.getName(), remunerationDto.getDescription(), remunerationDto.getValue());
    }
    public RemunerationDto mapToRemuneartionDto(Remuneration remuneration) {
        return new RemunerationDto(remuneration.getId(), remuneration.getDate(), remuneration.getName(), remuneration.getDescription(), remuneration.getValue());
    }
}
