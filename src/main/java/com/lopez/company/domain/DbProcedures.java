package com.lopez.company.domain;

import com.lopez.company.domain.dto.EmployeDto;
import com.lopez.company.domain.dto.InfoDto;
import com.lopez.company.domain.dto.PositionDto;
import com.lopez.company.domain.dto.RemunerationDto;
import com.lopez.company.exceptions.EmployeNotFoundException;
import com.lopez.company.exceptions.PositionNotFoundException;
import com.lopez.company.exceptions.RemunerationNotFoundException;
import com.lopez.company.mapper.EmployeMapper;
import com.lopez.company.mapper.InfoMapper;
import com.lopez.company.mapper.PositionMapper;
import com.lopez.company.mapper.RemunerationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    InfoMapper infoMapper;
    @Autowired
    DbService dbService;

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
        remunerationsDtoList.add(new RemunerationDto("2016-2-9", "Monthly bonus", "Monthly bonus for work efficiency", 2565.55));
        remunerationsDtoList.add(new RemunerationDto("2017-12-21", "Christmas bonus", "Christmas bonus every year", 1587.45));
        remunerationsDtoList.add(new RemunerationDto("2017-10-10", "One-time bonus", "Normal one-time bonus", 1200d));
        remunerationsDtoList.add(new RemunerationDto("2018-1-31", "January", "Salary for the month of January", 6900d));
        remunerationsDtoList.add(new RemunerationDto("2018-2-28", "February", "Salary for the month of February", 6900d));
        remunerationsDtoList.add(new RemunerationDto("2018-3-31", "March", "Salary for the month of March", 6900d));
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

    public Employe getOnlyEmployeOneRecord(long id) throws EmployeNotFoundException {
        return dbService.getEmploye(id).orElseThrow(EmployeNotFoundException::new);
    }

    public EmployeDto getRelatedRecordsWithEmploye(Employe employe) throws PositionNotFoundException, RemunerationNotFoundException {
        List<PositionDto> positionDtoList = dbService.getPosition(employe.getId()).stream()
                .map(p -> positionMapper.mapToPositionDto(p))
                .collect(Collectors.toList());
        List<RemunerationDto> remunerationDtoList = dbService.getRemuneration(employe.getId()).stream()
                .map(r -> remunerationMapper.mapToRemuneartionDto(r))
                .collect(Collectors.toList());
        EmployeDto employeDtoResult = employeMapper.mapToEmployeDto(employe);
        employeDtoResult.setPositionDtoList(positionDtoList);
        employeDtoResult.setRemunerationDtoList(remunerationDtoList);
        return employeDtoResult;
    }

    public List<EmployeDto> getAllEmployees() {
        List<EmployeDto> resultList = new ArrayList<>();
        List<Employe> listEmployeesFromDb = dbService.getAllEmployees();
        List<Position> listPositionsFromDb = dbService.getAllPositions();
        List<Remuneration> listRemunerationsFromDb = dbService.getAllRemunerations();
        EmployeDto employeDtoResult = null;
        PositionDto positionDtoResult = null;
        RemunerationDto remunerationDtoResult = null;
        long idResult;
        long idPositionResult;
        long idRemunerationResult;
        for (Employe employe : listEmployeesFromDb) {
            employeDtoResult = employeMapper.mapToEmployeDto(employe);
            idResult = employeDtoResult.getId();
            for (Position position : listPositionsFromDb) {
                positionDtoResult = positionMapper.mapToPositionDto(position);
                idPositionResult = position.getEmploye().getId();
                if (idResult == idPositionResult) {
                    employeDtoResult.getPositionDtoList().add(positionDtoResult);
                }
            }
            for (Remuneration remuneration : listRemunerationsFromDb) {
                remunerationDtoResult = remunerationMapper.mapToRemuneartionDto(remuneration);
                idRemunerationResult = remuneration.getEmploye().getId();
                if (idResult == idRemunerationResult) {
                    employeDtoResult.getRemunerationDtoList().add(remunerationDtoResult);
                }
            }
            resultList.add(employeDtoResult);
        }
        return resultList;
    }

    public EmployeDto getEmploye(Long id) throws EmployeNotFoundException, PositionNotFoundException, RemunerationNotFoundException {
        List<PositionDto> positionDtoList = dbService.getPosition(id).stream()
                .map(p -> positionMapper.mapToPositionDto(p))
                .collect(Collectors.toList());
        List<RemunerationDto> remunerationDtoList = dbService.getRemuneration(id).stream()
                .map(r -> remunerationMapper.mapToRemuneartionDto(r))
                .collect(Collectors.toList());
        EmployeDto resultEmployeDto = employeMapper.mapToEmployeDto(dbService.getEmploye(id).orElseThrow(EmployeNotFoundException::new));
        resultEmployeDto.setPositionDtoList(positionDtoList);
        resultEmployeDto.setRemunerationDtoList(remunerationDtoList);
        return resultEmployeDto;
    }

    public EmployeDto saveOnlyEmploye(EmployeDto employeDto) {
        dbService.saveEmploye(employeMapper.mapToEmploye(employeDto));
        return employeMapper.mapToEmployeDto(dbService.getOneEmploye(employeDto.getPesel().longValue()));
    }

    public EmployeDto saveEmployeWithAll(EmployeDto employeDto) throws EmployeNotFoundException, PositionNotFoundException, RemunerationNotFoundException {
        Employe employe = employeMapper.mapToEmploye(employeDto);
        Position position = null;
        Remuneration remuneration = null;
        for (PositionDto positionDto : employeDto.getPositionDtoList()) {
            position = positionMapper.mapToPosition(positionDto);
            position.setEmploye(employe);
            employe.getPosition().add(position);
        }
        for (RemunerationDto remunerationDto : employeDto.getRemunerationDtoList()) {
            remuneration = remunerationMapper.mapToRemuneration(remunerationDto);
            remuneration.setEmploye(employe);
            employe.getRemuneration().add(remuneration);
        }
        dbService.saveEmploye(employe);
        long resultId = employeMapper.mapToEmployeDto(dbService.getOneEmploye(employe.getPesel().longValue())).getId();
        return getEmploye((long) resultId);
    }

    public EmployeDto updateEmployeWithAll(EmployeDto employeDto) throws EmployeNotFoundException{
        Employe employe = employeMapper.mapToEmploye(employeDto);
        Position position = null;
        Remuneration remuneration = null;
        for (PositionDto positionDto : employeDto.getPositionDtoList()) {
            position = positionMapper.mapToPosition(positionDto);
            position.setEmploye(employe);
            employe.getPosition().add(position);
            dbService.savePosition(position);
        }
        for (RemunerationDto remunerationDto : employeDto.getRemunerationDtoList()) {
            remuneration = remunerationMapper.mapToRemuneration(remunerationDto);
            remuneration.setEmploye(employe);
            employe.getRemuneration().add(remuneration);
            dbService.saveRemuneration(remuneration);
        }
        dbService.saveEmploye(employe);
        return employeMapper.mapToEmployeDto(dbService.getEmploye(employeDto.getId()).orElseThrow(EmployeNotFoundException::new));
    }

    public List<EmployeDto> getAllInfo() {
        List<InfoDto> infoDtoList = dbService.getAllRecordsFromInfo().stream()
                .map(info -> infoMapper.mapToInfoDto(info))
                .collect(Collectors.toList());
        //EmployeDto employeDto = null;
        EmployeDto employeCheck = null;
        PositionDto positionDto = null;
        PositionDto positionDtoCheck = null;
        RemunerationDto remunerationDtoCheck = null;
        RemunerationDto remunerationDto = null;
        Set<EmployeDto> employeDtoSet = new HashSet<>();
        Set<PositionDto> positionDtoSet = new HashSet<>();
        Set<RemunerationDto> remunerationDtoSet = new HashSet<>();
        List<EmployeDto> result = new ArrayList<>();

        for (InfoDto infoDto : infoDtoList) {
            employeCheck = new EmployeDto(infoDto.getId(), infoDto.getFirstname(), infoDto.getLastname(), infoDto.getPesel(), infoDto.getSex());
            employeDtoSet.add(employeCheck);
        }
        for (EmployeDto employeDto : employeDtoSet) {
//            System.out.println("zmiana");
            for (InfoDto infoDto : infoDtoList) {
                if (employeDto.getId() == infoDto.getId()) {
//                    System.out.println("TAK " + infoDto.getPid() + "-" + infoDto.getPname());
                    positionDtoCheck = new PositionDto(infoDto.getPid(), infoDto.getPname());
                    if (!employeDto.getPositionDtoList().contains(positionDtoCheck)) {
//                        System.out.println("przed " + !employeDto.getPositionDtoList().contains(positionDtoCheck));
                        employeDto.getPositionDtoList().add(positionDtoCheck);
//                        System.out.println(employeDto.getFirstName() + " " + employeDto.getLastName() + " --> " + employeDto.getPositionDtoList().size());
//                        System.out.println("po " + !employeDto.getPositionDtoList().contains(positionDtoCheck));
                        positionDtoCheck = null;


                    }

                } else {
//                    System.out.println("NIE");
                }
            }


        }

        return result;

    }

}
