package com.lopez.company.repository;

import com.lopez.company.domain.Remuneration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RemunerationDao extends CrudRepository<Remuneration, Long> {
}
