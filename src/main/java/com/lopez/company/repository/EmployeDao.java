package com.lopez.company.repository;

import com.lopez.company.domain.Employe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface EmployeDao extends CrudRepository<Employe, Long> {
    @Override
    List<Employe> findAll();
    Optional<Employe> findById(Long id);
    @Override
    Employe save(Employe employe);
    @Override
    void delete(Long id);

    Employe findEmployeByPesel(BigDecimal pesel);

    @Query
    List<Employe> getSexMale();
    @Query
    List<Employe> getSexFemale();
    @Query
    List<Employe> getEmployeWithFullName(@Param("FIRSTNAME") String firstname, @Param("LASTNAME") String lastname);

}
