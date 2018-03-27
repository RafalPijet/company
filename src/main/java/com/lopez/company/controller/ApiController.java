package com.lopez.company.controller;

import com.lopez.company.domain.DbProcedures;
import com.lopez.company.domain.dto.EmployeDto;
import com.lopez.company.exceptions.EmployeNotFoundException;
import com.lopez.company.exceptions.PositionNotFoundException;
import com.lopez.company.exceptions.RemunerationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/company")
public class ApiController {

    @Autowired
    private DbProcedures dbProcedures;

    @RequestMapping(method = RequestMethod.GET, value = "/getEmployees")
    public List<EmployeDto> getEmployees() {
        return dbProcedures.getAllEmployees();
    }
    @RequestMapping(method = RequestMethod.GET, value = "/getEmploye")
    public EmployeDto getEmploye(@RequestParam Long id) throws EmployeNotFoundException, PositionNotFoundException, RemunerationNotFoundException {
        return dbProcedures.getEmploye(id);
    }
}
