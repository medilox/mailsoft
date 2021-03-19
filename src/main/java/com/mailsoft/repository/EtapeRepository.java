package com.mailsoft.repository;

import com.mailsoft.domain.Etape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EtapeRepository extends JpaRepository<Etape, Long>{


    List<Etape> findByCourrierId(Long id);

    List<Etape> findByStructureId(Long structureId);

    List<Etape> findByStructureIdAndTransmisParIsNotNull(Long id);

    List<Etape> findByCourrierUserIdAndStructureIdAndTransmisParIsNull(Long userId, Long structureId);

    @Query("SELECT etape FROM  Etape etape "
            + "WHERE (etape.enregistrePar.id = ?1) "
            + "OR ( etape.structure.id = ?2 AND (etape.recuPar.id = ?1 ) AND (etape.transmisPar is null OR etape.transmisPar != ?1) )")
    List<Etape> findEnregistrementsByCurrentUserAndReceptionsByCurrentUserStructure(Long userId, Long structureId);

    List<Etape> findByTransmisParId(Long userId);
    List<Etape> findByRecuParId(Long userId);
}