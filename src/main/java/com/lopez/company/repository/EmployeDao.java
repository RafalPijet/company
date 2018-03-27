package com.lopez.company.repository;

import com.lopez.company.domain.Employe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

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

    @Query
    List<Employe> getSexMale();
    @Query
    List<Employe> getSexFemale();
}
