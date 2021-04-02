package com.mailsoft.repository;

import com.mailsoft.domain.Courrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourrierRepository extends JpaRepository<Courrier, Long> {

    @Query("select courrier from Courrier courrier " +
            "where courrier.user.login = ?1 "
            + "AND CAST(courrier.id AS string) like lower(?2) "
            + "AND lower(courrier.refCourrier) like lower(?3) "
            + "AND  lower(courrier.objet) like lower(?4) "
            + "AND  lower(courrier.concernes) like lower(?5)")
    List<Courrier> findByUserIsCurrentUser(String userLogin, Long numCourrier, String refCourrier, String objet, String concernes);


    @Query("select courrier from Courrier courrier "
            + "WHERE CAST(courrier.id AS string) like ?1 "
            + "AND courrier.refCourrier like ?2 "
            + "AND courrier.objet like ?3 "
            + "AND courrier.concernes like ?4")
    List<Courrier> findAll(String numCourrier, String refCourrier, String objet, String concernes);

    Courrier findByRefCourrier(String refCourrier);

}
