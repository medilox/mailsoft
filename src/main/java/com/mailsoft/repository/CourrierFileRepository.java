package com.mailsoft.repository;

import com.mailsoft.domain.CourrierFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourrierFileRepository extends JpaRepository<CourrierFile, Long> {

    List<CourrierFile> findByCourrierId(Long id);
}