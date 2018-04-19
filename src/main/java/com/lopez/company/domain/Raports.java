package com.lopez.company.domain;

import com.lopez.company.domain.dto.EmployeDto;
import com.lopez.company.domain.dto.PositionDto;
import com.lopez.company.domain.dto.RemunerationDto;
import com.lopez.company.exceptions.EmployeNotFoundException;
import com.lopez.company.exceptions.PositionNotFoundException;
import com.lopez.company.exceptions.RemunerationNotFoundException;
import com.lopez.company.mapper.EmployeMapper;
import com.lopez.company.mapper.PositionMapper;
import com.lopez.company.mapper.RemunerationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Raports {
    private List<Employe> employeList = new ArrayList<>();
    private List<Position> positionList = new ArrayList<>();
    private List<Remuneration> remunerationList = new ArrayList<>();
    @Autowired
    private DbService dbService;
    @Autowired
    private EmployeMapper employeMapper;
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private RemunerationMapper remunerationMapper;

    public List<Remuneration> findRemuneartion(Date from, Date to) {
        List<Remuneration> resultList = dbService.getRemunerationFromToDate(from, to);
        resultList.stream()
                .forEach(r -> remunerationList.add(r));
        return resultList;
    }
    public List<Remuneration> findRemuneration(double low, double high) {
        List<Remuneration> resultList = dbService.getRemunerationWithLowAndHighValue(low, high);
        resultList.stream()
                .forEach(r -> remunerationList.add(r));
        return resultList;
    }
    public List<Remuneration> findRemuneration(Long employeId) throws RemunerationNotFoundException {
        List<Remuneration> resultList = dbService.getRemuneration(employeId);
        resultList.stream()
                .forEach(r -> remunerationList.add(r));
        return resultList;
    }
    public List<Remuneration> findRemuneration() {
        List<Remuneration> resultList = dbService.getAllRemunerations();
        resultList.stream()
                .forEach(r -> remunerationList.add(r));
        return resultList;
    }
    public List<Position> findPosition() {
        List<Position> resultList = dbService.getAllPositions();
        resultList.stream()
                .forEach(p -> positionList.add(p));
        return resultList;
    }
    public List<Position> findPosition(Long employeId) throws PositionNotFoundException {
        List<Position> resultList = dbService.getPosition(employeId);
        resultList.stream()
                .forEach(p -> positionList.add(p));
        return resultList;
    }
    public List<Position> findPosition(String name) {
        List<Position> resultList = dbService.getPositionWithSelectedName(name);
        resultList.stream()
                .forEach(p -> positionList.add(p));
        return resultList;
    }
    public List<Employe> findEmploye() {
        List<Employe> resultList = dbService.getAllEmployees();
        resultList.stream()
                .forEach(e -> employeList.add(e));
        return resultList;
    }
    public List<Employe> findEmploye(String firstname, String lastname) {
        List<Employe> resultList = dbService.getEmployeWithFullName(firstname, lastname);
        resultList.stream()
                .forEach(e -> employeList.add(e));
        return resultList;
    }
    public List<EmployeDto> getResultOfRaports() throws EmployeNotFoundException, RemunerationNotFoundException, PositionNotFoundException {
        List<EmployeDto> result = new ArrayList<>();
        EmployeDto employeDto = null;
        List<PositionDto> positionDtoList = new ArrayList<>();
        List<RemunerationDto> remunerationDtoList = new ArrayList<>();
        if (!employeList.isEmpty()) {
           for (Employe employe : employeList) {
               positionDtoList = dbService.getPosition(employe.getId()).stream()
                       .map(p -> positionMapper.mapToPositionDto(p))
                       .collect(Collectors.toList());
               remunerationDtoList = dbService.getRemuneration(employe.getId()).stream()
                       .map(r -> remunerationMapper.mapToRemuneartionDto(r))
                       .collect(Collectors.toList());
               employeDto = employeMapper.mapToEmployeDto(employe);
               employeDto.setPositionDtoList(positionDtoList);
               employeDto.setRemunerationDtoList(remunerationDtoList);
               result.add(employeDto);
           }
       }
       if (!positionList.isEmpty()) {
           for (Position position : positionList) {
               employeDto = employeMapper.mapToEmployeDto(dbService.getEmploye(position.getEmploye().getId()).orElseThrow(EmployeNotFoundException::new));
               employeDto.getPositionDtoList().add(positionMapper.mapToPositionDto(position));
               result.add(employeDto);
           }
       }
       if (!remunerationList.isEmpty()) {
           for (Remuneration remuneration : remunerationList) {
               employeDto = employeMapper.mapToEmployeDto(dbService.getEmploye(remuneration.getEmploye().getId()).orElseThrow(EmployeNotFoundException::new));
               employeDto.getRemunerationDtoList().add(remunerationMapper.mapToRemuneartionDto(remuneration));
               result.add(employeDto);
           }
       }
        return result;
    }
}
