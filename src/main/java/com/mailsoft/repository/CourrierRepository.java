package com.mailsoft.repository;

import com.mailsoft.domain.Courrier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourrierRepository extends JpaRepository<Courrier, Long> {

}
