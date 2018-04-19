package com.lopez.company.repository;

import com.lopez.company.domain.Position;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PositionDao extends CrudRepository<Position, Long> {

    @Override
    Position save(Position position);
    @Override
    void delete(Long id);

    @Query
    List<Position> getAllRecords();
    @Query
    List<Position> getEmployeId(@Param("EMPLOYEES_ID") Long employees_id);
    @Query
    List<Position> getPositionName(@Param("NAME") String name);
}
