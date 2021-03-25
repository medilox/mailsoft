package com.mailsoft.repository;

import com.mailsoft.domain.Courrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourrierRepository extends JpaRepository<Courrier, Long> {

    @Query("select courrier from Courrier courrier " +
            "where courrier.user.login = ?1")
    List<Courrier> findByUserIsCurrentUser(String currentUserLogin);

    Courrier findByRefCourrier(String refCourrier);

}
