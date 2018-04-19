package com.lopez.company.repository;

import com.lopez.company.domain.Info;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface InfoDao extends CrudRepository<Info, Long> {
    @Override
    List<Info> findAll();

    @Query(nativeQuery = true)
    List<Info> getAllInfo();

    List<Info> findById(long Id);
}
