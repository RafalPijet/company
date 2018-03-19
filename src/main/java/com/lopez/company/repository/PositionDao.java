package com.lopez.company.repository;

import com.lopez.company.domain.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PositionDao extends CrudRepository<Position, Long> {
}
