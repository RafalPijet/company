package com.lopez.company.repository;

import com.lopez.company.domain.Remuneration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface RemunerationDao extends CrudRepository<Remuneration, Long> {
    @Override
    Remuneration save(Remuneration remuneration);
    @Override
    void delete(Long id);

    @Query
    List<Remuneration> getAllRecords();
    @Query
    List<Remuneration> getEmployeId(@Param("EMPLOYEES_ID") Long employees_id);
    @Query
    List<Remuneration> getRemunerationWithLowAndHighValue(@Param("LOW") double low, @Param("HIGH") double high);
    @Query
    List<Remuneration> getRemunerationFromToDate(@Param("FROM") Date dateFrom, @Param("TO") Date dateTo);
}
