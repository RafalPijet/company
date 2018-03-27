package com.lopez.company;

import com.lopez.company.domain.*;
import com.lopez.company.domain.dto.EmployeDto;
import com.lopez.company.domain.dto.PositionDto;
import com.lopez.company.domain.dto.RemunerationDto;
import com.lopez.company.exceptions.EmployeNotFoundException;
import com.lopez.company.exceptions.PositionNotFoundException;
import com.lopez.company.exceptions.RemunerationNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeDaoTestSuite {
    @Autowired
    private DbService dbService;
    @Autowired
    private DbProcedures dbProcedures;

    @Test
    public void testSaveNewEmploye() throws EmployeNotFoundException {
        //Given
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse("2016-05-21");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Employe newEmploye = new Employe("Michael", "Jackson", new BigDecimal("52070812359"), "MALE");
        Position firstPosition = new Position("Developer");
        Remuneration remuneration1 = new Remuneration(date, "Monthly bonus", "Monthly bonus for work efficiency", 2565.55);
        //When
        newEmploye.getPosition().add(firstPosition);
        newEmploye.getRemuneration().add(remuneration1);
        firstPosition.setEmploye(newEmploye);
        remuneration1.setEmploye(newEmploye);
        dbService.saveEmploye(newEmploye);
        //Then
        Assert.assertEquals(newEmploye.getFirstName(), dbService.getEmploye(newEmploye.getId()).orElseThrow(EmployeNotFoundException::new).getFirstName());
        Assert.assertEquals(newEmploye.getPesel(), dbService.getEmploye(newEmploye.getId()).orElseThrow(EmployeNotFoundException::new).getPesel());
        //CleanUp
        dbService.deleteEmploye(newEmploye.getId());
    }
    @Test
    public void testCreateNewDataBase() {
        //Given
        int counter = 10;
        Employe resultEmploye = null;
        dbService.deleteAllRecords();
        dbProcedures.prepareEmployees();
        List<Employe> resultList = new ArrayList<>();
        for (int i = 1; i <= counter; i++) {
            resultEmploye = dbProcedures.createNewRecordRandom();
            resultList.add(resultEmploye);
            dbService.saveEmploye(resultEmploye);
        }
        //When
        long recordsQuantity = dbService.quantityOfRecords();
        //Then
        Assert.assertEquals(resultList.size(), recordsQuantity);
    }
    @Test
    public void testOfNamedQuerysForEmploye() {
        //Given
        //When
        List<Employe> manResult = dbService.getOnlyMaleEmployees();
        List<Employe> womenResult = dbService.getOnlyFemaleEmployees();
        System.out.println("Ilość facetów --> " + manResult.size());
        manResult.stream()
                .forEach(man -> System.out.println(man.getId() + ", " + man.getFirstName() + " " + man.getLastName() + ", " + man.getPesel() + ", " + man.getSex()));
        System.out.println("Ilość kobiet --> " + womenResult.size());
        womenResult.stream()
                .forEach(women -> System.out.println(women.getId() + ", " + women.getFirstName() + " " + women.getLastName() + ", " + women.getPesel() + ", " + women.getSex()));
        System.out.println();
        List<Position> positionsForOneEmploye = null;
        for (int i = 0; i < manResult.size(); i++) {
            positionsForOneEmploye = manResult.get(i).getPosition();
            System.out.println("For employ's name: " + manResult.get(i).getFirstName() + " " + manResult.get(i).getLastName() + " is " + positionsForOneEmploye.size() + " positions");
        }
        //Then
        Assert.assertEquals(5, manResult.size());
        Assert.assertEquals(5, womenResult.size());
    }
    @Test
    public void testNamedQuerysForPosition() throws PositionNotFoundException {
        //Given
        //When
        List<Position> positionList = dbService.getAllPositions();
        List<Position> positionListForEmployeId = dbService.getPosition((long) 237);
        System.out.println("Ilość rekordów w position --> " + positionList.size());
        System.out.println("Ilość rekordów w position dla jednego Employe --> " + positionListForEmployeId.size());
        positionListForEmployeId.stream()
                .forEach(e -> System.out.println(e.getId() + ", " + e.getName() + ", " + e.getEmploye().getId()));
        //Then
        Assert.assertEquals(dbService.quantityOfPositions(), positionList.size());
        Assert.assertEquals(3, positionListForEmployeId.size());
    }
    @Test
    public void testNamedQuerysForRemuneration() throws RemunerationNotFoundException {
        //Given
        //When
        List<Remuneration> remunerationList = dbService.getAllRemunerations();
        List<Remuneration> remunerationListForEmployeId = dbService.getRemuneration((long) 237);
        System.out.println("Ilość rekordów w remuneration --> " + remunerationList.size());
        System.out.println("Ilość rekordów w remuneration dla jednego Employe --> " + remunerationListForEmployeId.size());
        remunerationListForEmployeId.stream()
                .forEach(r -> System.out.println(r.getId() + ", " + r.getDate() + ", " + r.getName() + ", " + r.getDescription() + ", " + r.getValue() + ", " + r.getEmploye().getId()));
        //Then
        Assert.assertEquals(dbService.quantityOfRemunerations(), remunerationList.size());
        Assert.assertEquals(4, remunerationListForEmployeId.size());
    }
    @Test
    public void testGetAllEmployeesDtoFromDb() {
        //Given
        //When
        List<EmployeDto> resultList = dbProcedures.getAllEmployees();
        resultList.stream()
                .forEach(e -> System.out.println(e.getId() + ", " + e.getFirstName() + " " + e.getLastName() + " --> " + e.getPositionDtoList().size() + " --> " + e.getRemunerationDtoList().size()));
        List<PositionDto> positionsDtoList = resultList.stream()
                .flatMap(employeDto -> employeDto.getPositionDtoList().stream())
                .collect(Collectors.toList());
        List<RemunerationDto> remunerationDtoList = resultList.stream()
                .flatMap(employeDto -> employeDto.getRemunerationDtoList().stream())
                .collect(Collectors.toList());
        System.out.println("Quantity of Employees --> " + resultList.size());
        System.out.println("Quantity of Positions --> " + positionsDtoList.size());
        System.out.println("Quantity of Remunerations --> " + remunerationDtoList.size());

        //Then
        Assert.assertEquals(dbService.quantityOfRecords(), resultList.size());
        Assert.assertEquals(dbService.quantityOfPositions(), positionsDtoList.size());
        Assert.assertEquals(dbService.quantityOfRemunerations(), remunerationDtoList.size());
    }
    @Test
    public void testGetEmployeDtoWithId() throws EmployeNotFoundException, PositionNotFoundException, RemunerationNotFoundException {
        //Given
        //When
        EmployeDto employeDto = dbProcedures.getEmploye((long) 243);
        System.out.println(employeDto.getId() + ", " + employeDto.getFirstName() + " " + employeDto.getLastName() + ", " + employeDto.getPesel() + ", " + employeDto.getSex());
        employeDto.getPositionDtoList().stream()
                .forEach(p -> System.out.println(p.getId() + ", " + p.getName()));
        employeDto.getRemunerationDtoList().stream()
                .forEach(r -> System.out.println(r.getId() + ", " + r.getDate() + ", " + r.getName() + ", " + r.getDescription() + ", " + r.getValue()));
        //Then
        Assert.assertEquals(2, employeDto.getPositionDtoList().size());
        Assert.assertEquals(4, employeDto.getRemunerationDtoList().size());
    }
}
