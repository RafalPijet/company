package com.lopez.company.domain;

import com.lopez.company.domain.dto.EmployeDto;
import com.lopez.company.domain.dto.PositionDto;
import com.lopez.company.domain.dto.RemunerationDto;
import com.lopez.company.mapper.EmployeMapper;
import com.lopez.company.mapper.PositionMapper;
import com.lopez.company.mapper.RemunerationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class DbProcedures {
    private List<EmployeDto> employeesDtoList = new ArrayList<>();
    private List<PositionDto> positionsDtoList = new ArrayList<>();
    private List<RemunerationDto> remunerationsDtoList = new ArrayList<>();
    @Autowired
    EmployeMapper employeMapper;
    @Autowired
    PositionMapper positionMapper;
    @Autowired
    RemunerationMapper remunerationMapper;

    public void prepareEmployees() {
        employeesDtoList.add(new EmployeDto("Michael", "Jackson", new BigDecimal("52070812359"), "MALE"));
        employeesDtoList.add(new EmployeDto("John", "Travolta", new BigDecimal("91042269875"), "MALE"));
        employeesDtoList.add(new EmployeDto("Samuel", "Jackson", new BigDecimal("55111205287"), "MALE"));
        employeesDtoList.add(new EmployeDto("David", "Gahan", new BigDecimal("62050745214"), "MALE"));
        employeesDtoList.add(new EmployeDto("Martin", "L.Gore", new BigDecimal("63010242589"), "MALE"));
        employeesDtoList.add(new EmployeDto("Jennifer", "Connelly", new BigDecimal("70121252321"), "FEMALE"));
        employeesDtoList.add(new EmployeDto("Camilla", "Belle", new BigDecimal("86100285439"), "FEMALE"));
        employeesDtoList.add(new EmployeDto("Scarlett", "Johansson", new BigDecimal("84112214785"), "FEMALE"));
        employeesDtoList.add(new EmployeDto("Cameron", "Diaz", new BigDecimal("72083056931"), "FEMALE"));
        employeesDtoList.add(new EmployeDto("Winona", "Ryder", new BigDecimal("71102956482"), "FEMALE"));
    }
    public void preparePositions() {
        positionsDtoList.clear();
        positionsDtoList.add(new PositionDto("Developer"));
        positionsDtoList.add(new PositionDto("Engineer"));
        positionsDtoList.add(new PositionDto("Secretary"));
        positionsDtoList.add(new PositionDto("Software tester"));
        positionsDtoList.add(new PositionDto("Office worker"));
    }
    public void prepareRemunerations() {
        remunerationsDtoList.clear();
        remunerationsDtoList.add(new RemunerationDto(dateUtility(2016,2, 9), "Monthly bonus", "Monthly bonus for work efficiency", 2565.55));
        remunerationsDtoList.add(new RemunerationDto(dateUtility(2017,12, 21), "Christmas bonus", "Christmas bonus every year", 1587.45));
        remunerationsDtoList.add(new RemunerationDto(new Date(), "One-time bonus", "Normal one-time bonus", 1200d));
        remunerationsDtoList.add(new RemunerationDto(dateUtility(2018, 1, 31), "January", "Salary for the month of January", 6900d));
        remunerationsDtoList.add(new RemunerationDto(dateUtility(2018, 2, 28), "February", "Salary for the month of February", 6900d));
        remunerationsDtoList.add(new RemunerationDto(dateUtility(2018, 3, 31), "March", "Salary for the month of March", 6900d));
    }

    private Date dateUtility(int year, int month, int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try {
            result = format.parse(String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Employe createNewRecordRandom() {
        Random generator = new Random();
        preparePositions();
        prepareRemunerations();
        int positionCounter = generator.nextInt(positionsDtoList.size());
        if (positionCounter == 0) {
            positionCounter = 1;
        }
        int remunerationCounter = generator.nextInt(remunerationsDtoList.size());
        if (remunerationCounter == 0) {
            remunerationCounter = 1;
        }
        PositionDto randomPositionDto = null;
        RemunerationDto randomRemunerationDto = null;
        Position randomPosition = null;
        Remuneration randomRemuneration = null;
        EmployeDto employeDtoResult = employeesDtoList.get(generator.nextInt(employeesDtoList.size()));
        employeesDtoList.remove(employeDtoResult);
        Employe employeResult = employeMapper.mapToEmploye(employeDtoResult);
        for (int i = 0; i <= generator.nextInt(positionCounter); i++) {
            randomPositionDto = positionsDtoList.get(generator.nextInt(positionsDtoList.size()));
            positionsDtoList.remove(randomPositionDto);
            randomPosition = positionMapper.mapToPosition(randomPositionDto);
            randomPosition.setEmploye(employeResult);
            employeResult.getPosition().add(randomPosition);
        }
        for (int i = 0; i <= generator.nextInt(remunerationCounter); i++) {
            randomRemunerationDto = remunerationsDtoList.get(generator.nextInt(remunerationsDtoList.size()));
            remunerationsDtoList.remove(randomRemunerationDto);
            randomRemuneration = remunerationMapper.mapToRemuneration(randomRemunerationDto);
            randomRemuneration.setEmploye(employeResult);
            employeResult.getRemuneration().add(randomRemuneration);
        }
        return employeResult;
    }

}
