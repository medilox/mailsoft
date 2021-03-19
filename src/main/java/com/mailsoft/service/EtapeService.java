package com.mailsoft.service;

import com.mailsoft.domain.*;
import com.mailsoft.dto.EtapeDto;
import com.mailsoft.repository.*;
import com.mailsoft.security.SecurityUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Etape.
 */
@Service
@Transactional
public class EtapeService {

    private final Logger log = LoggerFactory.getLogger(EtapeService.class);

    @Autowired
    private EtapeRepository etapeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private CourrierRepository courrierRepository;

    @Autowired
    private CourrierService courrierService;


    /**
     * Save a etape.
     *
     * @param etapeDto the entity to save
     * @return the persisted entity
     */
    public ResponseEntity<?> save(EtapeDto etapeDto) {
        log.debug("Request to save Etape : {}", etapeDto);

        Etape etape = new Etape();

        Courrier courrier = courrierRepository.findOne(etapeDto.getCourrierId());
        etape.setCourrier(courrier);

        Structure structure = structureRepository.findOne(etapeDto.getStructureId());
        etape.setStructure(structure);

        //set created date;
        String pattern = "yyyy-MM-dd HH:mm";
        LocalDateTime datetime = new LocalDateTime();
        etape.setCreatedDate(datetime.toString(pattern));

        etape.setInstructions(etapeDto.getInstructions());

        if(etapeDto.getRecuParId() != null){
            //previous must be non null
            //copy instructions from previous
            if(courrier.getLastEtape() != null){
                etape.setInstructions(courrier.getLastEtape().getInstructions());
            }
            User recuPar = userRepository.findOne(etapeDto.getRecuParId());
            etape.setRecuPar(recuPar);
        }

        if(etapeDto.getTransmisParId() != null){
            User transmisPar = userRepository.findOne(etapeDto.getTransmisParId());
            etape.setTransmisPar(transmisPar);
        }

        if(etapeDto.getEnregistreParId() != null){
            User enregistrePar = userRepository.findOne(etapeDto.getEnregistreParId());
            etape.setEnregistrePar(enregistrePar);
        }

        if(courrier.getLastEtape() != null){
            etape.setPrevious(courrier.getLastEtape());
        }

        Etape result = etapeRepository.save(etape);
        if(result != null){
            courrier.setLastEtape(result);
            courrierRepository.save(courrier);
        }
        return new ResponseEntity<EtapeDto>(new EtapeDto().createDTO(result), HttpStatus.CREATED);
    }


    public ResponseEntity<EtapeDto> update(EtapeDto etapeDto) {
        log.debug("Request to save Etape : {}", etapeDto);

        Etape etape = etapeRepository.findOne(etapeDto.getId());

        Etape result = etapeRepository.save(etape);
        return new ResponseEntity<EtapeDto>(new EtapeDto().createDTO(result), HttpStatus.CREATED);
    }


    public List<EtapeDto> findByStructure(Long structureId) {
        log.debug("Request to get all Etapes");

        List<Etape> etapes = etapeRepository.findByStructureId(structureId);

        List<EtapeDto> etapeDtos = new ArrayList<>();
        for(Etape etape: etapes){
            etapeDtos.add(new EtapeDto().createDTO(etape));
        }
        return etapeDtos;
    }

    public List<EtapeDto> findTransmissionsByCurrentUserStructure() {
        User user = userRepository.findByLoginOrEmail(SecurityUtils.getCurrentUserLogin());
        List<Etape> etapes = etapeRepository.findByStructureIdAndTransmisParIsNotNull(user.getStructure().getId());

        List<EtapeDto> etapeDtos = new ArrayList<>();
        for(Etape etape: etapes){
            if(etape.getCourrier().getLastEtape().getId() == etape.getId()){
                if(etape.getTransmisPar().getId() != user.getId())
                etapeDtos.add(new EtapeDto().createDTO(etape));
            }
        }
        return etapeDtos;
    }

    public List<EtapeDto> findTransmissionsByCurrentUser() {
        User user = userRepository.findByLoginOrEmail(SecurityUtils.getCurrentUserLogin());
        List<Etape> etapes = etapeRepository.findByTransmisParId(user.getId());

        List<EtapeDto> etapeDtos = new ArrayList<>();
        for(Etape etape: etapes){
            etapeDtos.add(new EtapeDto().createDTO(etape));
        }
        return etapeDtos;
    }

    public List<EtapeDto> findReceptionsAndEnregistrementsByCurrentUserStructure() {

        User user = userRepository.findByLoginOrEmail(SecurityUtils.getCurrentUserLogin());
        //List<Etape> etapes = etapeRepository.findByStructureIdAndTransmisParIsNotNull(user.getStructure().getId());
        //get courriers recus and courriers enregistrés
        List<Etape> etapes = etapeRepository.findEnregistrementsByCurrentUserAndReceptionsByCurrentUserStructure(user.getId(), user.getStructure().getId());

        List<EtapeDto> etapeDtos = new ArrayList<>();
        for(Etape etape: etapes){
            //if etapeIsLastEtapeForCourrier()
            if(etape.getCourrier().getLastEtape().getId() == etape.getId()){
                etapeDtos.add(new EtapeDto().createDTO(etape));
            }
        }
        return etapeDtos;
    }

    public List<EtapeDto> findReceptionsByCurrentUser() {
        User user = userRepository.findByLoginOrEmail(SecurityUtils.getCurrentUserLogin());
        List<Etape> etapes = etapeRepository.findByRecuParId(user.getId());

        List<EtapeDto> etapeDtos = new ArrayList<>();
        for(Etape etape: etapes){
            etapeDtos.add(new EtapeDto().createDTO(etape));
        }
        return etapeDtos;
    }


    public List<EtapeDto> findByCourrier(Long courrierId) {

        List<Etape> etapes = etapeRepository.findByCourrierId(courrierId);
        List<EtapeDto> etapeDtos = new ArrayList<>();
        for(Etape etape: etapes){
            etapeDtos.add(new EtapeDto().createDTO(etape));
        }
        return etapeDtos;
    }

    /**
     *  Get one etape by id.
     *
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public EtapeDto findOne(Long etapeId) {
        log.debug("Request to get Etape : {}");
        Etape etape = etapeRepository.findOne(etapeId);
        return new EtapeDto().createDTO(etape);
    }

    /**
     *  Delete the  etape by id.
     *
     */
    public void delete(Long etapeId) throws Exception {
        log.debug("Request to delete Etape : {}");

        Etape etape = etapeRepository.findOne(etapeId);

        if(Optional.ofNullable(etape).isPresent()){
            if(etape.getCourrier().getLastEtape().getId() != etapeId){
                throw new Exception("You should not do this !");
            }
            if(etape.getPrevious() != null){
                etape.getCourrier().setLastEtape(etape.getPrevious());
            }
            else{
                etape.getCourrier().setLastEtape(null);
            }
            etapeRepository.delete(etapeId);
        }
    }

}
