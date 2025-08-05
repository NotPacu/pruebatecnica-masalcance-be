package com.pruebatecnica.Respositories;

import com.pruebatecnica.Entities.QueryLogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QueryLogRespository  extends JpaRepository<QueryLogsEntity, Long> {


    Optional<QueryLogsEntity> findById(Long id);

    List<QueryLogsEntity> findAllByActiveTrue();
}
