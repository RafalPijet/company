package com.lopez.company.mapper;

import com.lopez.company.domain.Employe;
import com.lopez.company.domain.dto.EmployeDto;
import org.springframework.stereotype.Component;

@Component
public class EmployeMapper {

    public Employe mapToEmploye(final EmployeDto employeDto) {
        return new Employe(employeDto.getFirstName(), employeDto.getLastName(), employeDto.getPesel(), employeDto.getSex());
    }
    public EmployeDto mapToEmployeDto(final Employe employe) {
        return new EmployeDto(employe.getId(), employe.getFirstName(), employe.getLastName(), employe.getPesel(), employe.getSex());
    }

}
