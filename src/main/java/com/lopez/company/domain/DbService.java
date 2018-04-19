package com.lopez.company.domain;

import com.lopez.company.exceptions.EmployeNotFoundException;
import com.lopez.company.exceptions.PositionNotFoundException;
import com.lopez.company.exceptions.RemunerationNotFoundException;
import com.lopez.company.repository.EmployeDao;
import com.lopez.company.repository.InfoDao;
import com.lopez.company.repository.PositionDao;
import com.lopez.company.repository.RemunerationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DbService {
    @Autowired
    private EmployeDao employeDao;
    @Autowired
    private PositionDao positionDao;
    @Autowired
    private RemunerationDao remunerationDao;
    @Autowired
    private InfoDao infoDao;

    public List<Info> getAllRecordsFromInfo() {
        List<Info> result = infoDao.findAll();
        System.out.println("???????????????????????????????????????/");
        result.stream()
                .forEach(info -> System.out.println(info.getFirstname() + " " + info.getLastname() + " -- " + info.getPname() + " -- " + info.getValue() + " -- " + info.getName()));
        return infoDao.findAll();
    }

    public List<Info> getRecordsById(long id) {
        List<Info> result = infoDao.findById(id);
        System.out.println("???????????????????????????????????????/");
        result.stream()
                .forEach(info -> System.out.println(info.getFirstname() + " " + info.getLastname() + " -- " + info.getPname() + " -- " + info.getValue() + " -- " + info.getName()));
        return result;
    }


    public List<Employe> getAllEmployees() {
        return employeDao.findAll();
    }
    public Optional<Employe> getEmploye(final Long id) {
        return employeDao.findById(id);
    }
    public Employe saveEmploye(final Employe employe) {
        return employeDao.save(employe);
    }
    public void deleteEmploye(final Long id) throws EmployeNotFoundException {
        if (employeDao.exists(id)) {
            employeDao.delete(id);
        } else {
            throw new EmployeNotFoundException();
        }
    }
    public Employe getOneEmploye(Long pesel) {
        return employeDao.findEmployeByPesel(new BigDecimal(pesel.toString()));
    }
    public void deleteAllRecords() {
        employeDao.deleteAll();
    }
    public long quantityOfRecords() {
        return employeDao.count();
    }
    public List<Employe> getOnlyMaleEmployees() {
        return employeDao.getSexMale();
    }
    public List<Employe> getOnlyFemaleEmployees() {
        return employeDao.getSexFemale();
    }
    public List<Employe> getEmployeWithFullName(String firstname, String lastname) {
        return employeDao.getEmployeWithFullName(firstname, lastname);
    }
    public List<Position> getAllPositions() {
        return positionDao.getAllRecords();
    }
    public List<Position> getPosition(final Long id) throws PositionNotFoundException {
        if (employeDao.exists(id)) {
            return positionDao.getEmployeId(id);
        } else {
            throw new PositionNotFoundException();
        }
    }
    public long quantityOfPositions() {
        return positionDao.count();
    }
    public List<Position> getPositionWithSelectedName(String name) {
        return positionDao.getPositionName(name);
    }
    public Position savePosition(final Position position) {
        return positionDao.save(position);
    }
    public void deletePosition(final Long id) throws PositionNotFoundException {
        if (positionDao.exists(id)) {
            positionDao.delete(id);
        } else {
            throw new PositionNotFoundException();
        }
    }
    public List<Remuneration> getAllRemunerations() {
        return remunerationDao.getAllRecords();
    }
    public List<Remuneration> getRemuneration(final Long id) throws RemunerationNotFoundException {
        if (employeDao.exists(id)) {
            return remunerationDao.getEmployeId((long) id);
        } else {
            throw new RemunerationNotFoundException();
        }
    }
    public long quantityOfRemunerations() {
        return remunerationDao.count();
    }
    public Remuneration saveRemuneration(final Remuneration remuneration) {
        return remunerationDao.save(remuneration);
    }
    public void deleteRemuneration(final Long id) throws RemunerationNotFoundException {
        if (remunerationDao.exists(id)) {
            remunerationDao.delete(id);
        } else {
            throw new RemunerationNotFoundException();
        }
    }
    public List<Remuneration> getRemunerationWithLowAndHighValue(double low, double high) {
        return remunerationDao.getRemunerationWithLowAndHighValue(low, high);
    }
    public List<Remuneration> getRemunerationFromToDate(Date dateFrom, Date dateTo) {
        return remunerationDao.getRemunerationFromToDate(dateFrom, dateTo);
    }
}
