package com.lopez.company.controller;

import com.lopez.company.domain.DbProcedures;
import com.lopez.company.domain.DbService;
import com.lopez.company.domain.dto.EmployeDto;
import com.lopez.company.domain.dto.InfoDto;
import com.lopez.company.exceptions.EmployeNotFoundException;
import com.lopez.company.exceptions.PositionNotFoundException;
import com.lopez.company.exceptions.RemunerationNotFoundException;
import com.lopez.company.mapper.InfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("v1/company")
public class ApiController {

    @Autowired
    private DbProcedures dbProcedures;
    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private DbService dbService;

    @RequestMapping(method = RequestMethod.GET, value = "/getEmployees")
    public List<EmployeDto> getEmployees() {
        return dbProcedures.getAllEmployees();
    }
    @RequestMapping(method = RequestMethod.GET, value = "/getEmploye")
    public EmployeDto getEmploye(@RequestParam Long id) throws EmployeNotFoundException, PositionNotFoundException, RemunerationNotFoundException {
        return dbProcedures.getEmploye(id);
    }
    @RequestMapping(method = RequestMethod.POST, value = "/createOnlyEmploye", consumes = APPLICATION_JSON_VALUE)
    public String createOnlyEmploye(@RequestBody EmployeDto employeDto) {
        EmployeDto result = dbProcedures.saveOnlyEmploye(employeDto);
        if (result.getPesel().equals(employeDto.getPesel())) {
            return "the new entry has been saved in the data base in number id = " + result.getId();
        } else {
            return "data write error";
        }
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/updateOnlyEmploye")
    public EmployeDto updateOnlyEmploye(@RequestBody EmployeDto employeDto) {
        return dbProcedures.saveOnlyEmploye(employeDto);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/createEmploye", consumes = APPLICATION_JSON_VALUE)
    public EmployeDto createEmploye(@RequestBody EmployeDto employeDto) throws EmployeNotFoundException, PositionNotFoundException, RemunerationNotFoundException {
        return dbProcedures.saveEmployeWithAll(employeDto);
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/updateEmploye")
    public EmployeDto updateEmploye(@RequestBody EmployeDto employeDto) throws EmployeNotFoundException {
        return dbProcedures.updateEmployeWithAll(employeDto);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/getAllInfo")
    public List<InfoDto> getAllInfo() {
        return dbService.getAllRecordsFromInfo().stream()
                .map(info -> infoMapper.mapToInfoDto(info))
                .collect(Collectors.toList());
    }
}
