package com.lopez.company;

import com.lopez.company.domain.DbProcedures;
import com.lopez.company.domain.Employe;
import com.lopez.company.domain.Position;
import com.lopez.company.domain.Remuneration;
import com.lopez.company.repository.EmployeDao;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeDaoTestSuite {
    @Autowired
    private EmployeDao employeDao;
    @Autowired
    private DbProcedures dbProcedures;

    @Test
    public void testSaveNewEmploye() {
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
        employeDao.save(newEmploye);
        //Then
        Assert.assertEquals(newEmploye.getFirstName(), employeDao.findOne(newEmploye.getId()).getFirstName());
        Assert.assertEquals(newEmploye.getPesel(), employeDao.findOne(newEmploye.getId()).getPesel());
        //CleanUp
        employeDao.delete(newEmploye.getId());
    }
    @Test
    public void testCreateNewDataBase() {
        //Given
        int counter = 10;
        Employe resultEmploye = null;
        employeDao.deleteAll();
        dbProcedures.prepareEmployees();
        List<Employe> resultList = new ArrayList<>();
        for (int i = 1; i <= counter; i++) {
            resultEmploye = dbProcedures.createNewRecordRandom();
            resultList.add(resultEmploye);
            employeDao.save(resultEmploye);
        }
        //When
        long recordsQuantity = employeDao.count();
        //Then
        Assert.assertEquals(resultList.size(), recordsQuantity);
    }
}
