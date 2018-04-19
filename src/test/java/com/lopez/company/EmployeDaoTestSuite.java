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
    @Autowired
    private Raports raports;

    @Test
    public void testSaveNewEmploye() throws EmployeNotFoundException {
        //Given
        Employe newEmploye = new Employe("Michael", "Jackson", new BigDecimal("53070812359"), "MALE");
        Position firstPosition = new Position("Developer");

        Remuneration remuneration = new Remuneration(new Date(), "Monthly bonus", "Monthly bonus for work efficiency", 2565.55);
        //When
        newEmploye.getPosition().add(firstPosition);
        newEmploye.getRemuneration().add(remuneration);
        firstPosition.setEmploye(newEmploye);
        remuneration.setEmploye(newEmploye);
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
    public void testGetOneEmployeWithRelatedRecords() throws EmployeNotFoundException, RemunerationNotFoundException, PositionNotFoundException {
        Employe employe = dbProcedures.getOnlyEmployeOneRecord((long) 51);
        System.out.println("Employe name --> " + employe.getFirstName() + " " + employe.getLastName());
        EmployeDto employeDto = dbProcedures.getRelatedRecordsWithEmploye(employe);
        System.out.println("Quantity of positions --> " + employeDto.getPositionDtoList().size());
        System.out.println("Quantity of remunerations --> " + employeDto.getRemunerationDtoList().size());
    }
    @Test
    public void testGetPositionWithSelectedName() {
        List<Position> positionList = dbService.getPositionWithSelectedName("Engineer");
        System.out.println("Quantity of records with Engineer --> " + positionList.size());
        positionList.stream()
                .forEach(p -> System.out.println(p.getEmploye().getFirstName() + " " + p.getEmploye().getLastName()));
    }
    @Test
    public void testGetEmployeWithFullName() {
        List<Employe> employeList = dbService.getEmployeWithFullName("Jennifer", "Connelly");
        System.out.println("Quantity records with fullname of employe --> " + employeList.size());
    }
    @Test
    public void testGetRemunerationWithLowAndHighValue() {
        List<Remuneration> remunerationList = dbService.getRemunerationWithLowAndHighValue(1500d, 3000d);
        System.out.println("Quantity records between low and high value --> " + remunerationList.size());
        remunerationList.stream()
                .forEach(r -> System.out.println(r.getEmploye().getFirstName() + " " + r.getEmploye().getLastName() + " --> " + r.getName() + ", " + r.getValue()));
    }
    @Test
    public void testGetRemunerationBetweenFromAndToDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date from = null;
        Date to = null;
        List<Remuneration> remunerationList = new ArrayList<>();
        try {
            from = format.parse("2017-01-01");
            to = format.parse("2017-12-31");
            remunerationList = dbService.getRemunerationFromToDate(from, to);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Quantity records between from and to date --> " + remunerationList.size());
        remunerationList.stream()
                .forEach(r -> System.out.println(r.getEmploye().getFirstName() + " " + r.getEmploye().getLastName() + ", " + r.getDate() + ", " + r.getName() + ", " + r.getValue()));
    }
    @Test
    public void testPrepareRaport() throws EmployeNotFoundException, PositionNotFoundException, RemunerationNotFoundException {
        List<Remuneration> resultRemuneration = raports.findRemuneration(1500d, 3000d);
        List<Position> resultPosition = raports.findPosition("Developer");
        List<Employe> resultEmploye = raports.findEmploye("Camilla", "Belle");
        List<Employe> resultNextEmploye = raports.findEmploye("Jennifer", "Connelly");
        System.out.println("EmployeesList --> " + resultEmploye.size());
        System.out.println("PositionList --> " + resultPosition.size());
        System.out.println("RemunearationList --> " + resultRemuneration.size());
        List<EmployeDto> result = raports.getResultOfRaports();
        result.stream()
                .forEach(e -> System.out.println(e.getFirstName() + " " + e.getLastName() + " --> " + e.getPositionDtoList().size() + ";" + e.getRemunerationDtoList().size()));
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
        List<Position> positionListForEmployeId = dbService.getPosition((long) 51);
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
        List<Remuneration> remunerationListForEmployeId = dbService.getRemuneration((long) 51);
        System.out.println("Ilość rekordów w remuneration --> " + remunerationList.size());
        System.out.println("Ilość rekordów w remuneration dla jednego Employe --> " + remunerationListForEmployeId.size());
        remunerationListForEmployeId.stream()
                .forEach(r -> System.out.println(r.getId() + ", " + r.getDate() + ", " + r.getName() + ", " + r.getDescription() + ", " + r.getValue() + ", " + r.getEmploye().getId()));
        //Then
        Assert.assertEquals(dbService.quantityOfRemunerations(), remunerationList.size());
        Assert.assertEquals(3, remunerationListForEmployeId.size());
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
        EmployeDto employeDto = dbProcedures.getEmploye((long) 54);
        System.out.println(employeDto.getId() + ", " + employeDto.getFirstName() + " " + employeDto.getLastName() + ", " + employeDto.getPesel() + ", " + employeDto.getSex());
        employeDto.getPositionDtoList().stream()
                .forEach(p -> System.out.println(p.getId() + ", " + p.getName()));
        employeDto.getRemunerationDtoList().stream()
                .forEach(r -> System.out.println(r.getId() + ", " + r.getDate() + ", " + r.getName() + ", " + r.getDescription() + ", " + r.getValue()));
        //Then
        Assert.assertEquals(2, employeDto.getPositionDtoList().size());
        Assert.assertEquals(1, employeDto.getRemunerationDtoList().size());
    }
    @Test
    public void testSaveOnlyOneEmploye() {
        //Given
        //When
        EmployeDto employeDto = new EmployeDto("Salma", "Hayek", new BigDecimal("66090211339"), "FEMALE");
        EmployeDto resultEmployeDto = dbProcedures.saveOnlyEmploye(employeDto);
        //Then
        Assert.assertEquals(employeDto.getFirstName(), resultEmployeDto.getFirstName());
        Assert.assertEquals(employeDto.getPesel(), resultEmployeDto.getPesel());
        Assert.assertNotNull(resultEmployeDto.getId());
        Assert.assertNull(employeDto.getId());
    }
    @Test
    public void testGetInfo() {
        List<Info> infoList = dbService.getAllRecordsFromInfo();
        System.out.println("GO --> ");
        infoList.forEach(info -> System.out.println(info.getId() + ": " + info.getFirstname() + " " + info.getLastname() + ", " + info.getPesel() + ", " + info.getSex() + " --> " + info.getPid() + ":" + info.getPname()
        + " <-- " + info.getRid() + ":" + info.getName() + " - " + info.getDescription() + " : " + info.getDate() + " : " + info.getValue()));
        List<EmployeDto> employeDtoList = dbProcedures.getAllInfo();
        System.out.println(employeDtoList.size() + " z " + infoList.size() + " to:");
    /*    employeDtoList.stream()
                .forEach(employeDto -> {
                    System.out.println(employeDto.getId() + ": " + employeDto.getFirstName() + " " + employeDto.getLastName() + " - " + employeDto.getPesel() + " - " + employeDto.getSex() + "Quantity of positions: " + employeDto.getPositionDtoList().size() + " Quantity od remunerations: " + employeDto.getRemunerationDtoList().size());
                    System.out.println("Positions for Employe");
                    employeDto.getPositionDtoList().forEach(positionDto -> System.out.println(positionDto.getId() + ": " + positionDto.getName()));
                    System.out.println("Remunerations for Employe");
                    employeDto.getRemunerationDtoList().forEach(remunerationDto -> System.out.println(remunerationDto.getId() + ": " + remunerationDto.getName() + ", " + remunerationDto.getDescription() + ", " + remunerationDto.getDate() + " --> " + remunerationDto.getValue()));
                    System.out.println("---------------------------");
                }); */
    }
}
