package com.mailsoft.service;

import com.mailsoft.domain.Courrier;
import com.mailsoft.domain.User;
import com.mailsoft.domain.enumerations.NatureCourrier;
import com.mailsoft.dto.CourrierDto;
import com.mailsoft.repository.RoleRepository;
import com.mailsoft.repository.CourrierRepository;
import com.mailsoft.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service Implementation for managing Courrier.
 */
@Service
@Transactional
public class CourrierService {

    private final Logger log = LoggerFactory.getLogger(CourrierService.class);

    @Autowired
    private CourrierRepository courrierRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;


    /**
     * Save a courrier.
     *
     * @param courrierDto the entity to save
     * @return the persisted entity
     */
    public ResponseEntity<CourrierDto> save(CourrierDto courrierDto) {

        log.debug("Request to save Courrier : {}", courrierDto);
        Courrier courrier = new Courrier();
        courrier.setId(courrierDto.getId());

        courrier.setRefCourrier(courrierDto.getRefCourrier());
        courrier.setNumCourrier(courrierDto.getNumCourrier());
        courrier.setInitiateur(courrierDto.getInitiateur());
        courrier.setObjet(courrierDto.getObjet());
        courrier.setDateEnvoi(courrierDto.getDateEnvoi());
        courrier.setDateReception(courrierDto.getDateReception());
        courrier.setNatureCourrier(NatureCourrier.fromValue(courrierDto.getNatureCourrier()));
        courrier.setConcernes(courrierDto.getConcernes());


        if(courrierDto.getRecuParId() != null){
            User recuPar = userRepository.findOne(courrierDto.getRecuParId());
            courrier.setRecuPar(recuPar);
        }

        Courrier result = courrierRepository.save(courrier);
        return new ResponseEntity<CourrierDto>(new CourrierDto().createDTO(result), HttpStatus.CREATED);
    }


    public ResponseEntity<CourrierDto> update(CourrierDto courrierDto) {
        log.debug("Request to save Courrier : {}", courrierDto);
        Courrier courrier = courrierRepository.findOne(courrierDto.getId());

        courrier.setRefCourrier(courrierDto.getRefCourrier());
        courrier.setNumCourrier(courrierDto.getNumCourrier());
        courrier.setInitiateur(courrierDto.getInitiateur());
        courrier.setObjet(courrierDto.getObjet());
        courrier.setDateEnvoi(courrierDto.getDateEnvoi());
        courrier.setDateReception(courrierDto.getDateReception());
        courrier.setNatureCourrier(NatureCourrier.fromValue(courrierDto.getNatureCourrier()));
        courrier.setConcernes(courrierDto.getConcernes());

        if(courrierDto.getRecuParId() != null){
            User recuPar = userRepository.findOne(courrierDto.getRecuParId());
            courrier.setRecuPar(recuPar);
        }
        else{
            courrier.setRecuPar(null);
        }

        Courrier result = courrierRepository.save(courrier);

        return new ResponseEntity<CourrierDto>(new CourrierDto().createDTO(result), HttpStatus.CREATED);
    }

    /**
     *  Get all the courriers by user id.
     *
     *  @return the list of entities
     */
    public List<CourrierDto> findAll(){
        log.debug("Request to get all Courriers");

        List<Courrier> courriers = courrierRepository.findAll();

        List<CourrierDto> courrierDtos = new ArrayList<>();

        for (Courrier courrier : courriers)
            courrierDtos.add(new CourrierDto().createDTO(courrier));

        return  courrierDtos;
    }


    /**
     *  Get one courrier by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CourrierDto findOne(Long id) {
        log.debug("Request to get Courrier : {}", id);
        Courrier courrier = courrierRepository.findOne(id);
        return new CourrierDto().createDTO(courrier);
    }

    /**
     *  Delete the  courrier by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Courrier : {}", id);
        Courrier courrier = courrierRepository.findOne(id);

        if(Optional.ofNullable(courrier).isPresent()){
            courrierRepository.delete(id);
        }
    }

}