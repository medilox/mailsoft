package com.mailsoft.rest;

import com.mailsoft.domain.Courrier;
import com.mailsoft.dto.CourrierDto;
import com.mailsoft.dto.EtapeDto;
import com.mailsoft.repository.CourrierRepository;
import com.mailsoft.repository.EtapeRepository;
import com.mailsoft.repository.StructureRepository;
import com.mailsoft.repository.UserRepository;
import com.mailsoft.security.SecurityUtils;
import com.mailsoft.service.EtapeService;
import com.mailsoft.utils.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Etape.
 */
@CrossOrigin
@RestController
public class EtapeResource {

    private final Logger log = LoggerFactory.getLogger(EtapeResource.class);

    @Autowired
    private CourrierRepository courrierRepository;

    @Autowired
    private EtapeService etapeService;

    @Autowired
    private EtapeRepository etapeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StructureRepository structureRepository;

    /**
     * POST  /etapes : Create a new etape.
     *
     * @param etapeDto the etape to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etape, or with status 400 (Bad Request) if the etape has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api/etapes")
    public ResponseEntity<?> createEtape(@Valid @RequestBody EtapeDto etapeDto) throws URISyntaxException {
        log.debug("REST request to save Etape : {}", etapeDto);

        if(etapeDto.getCourrierId() == null || etapeDto.getStructureId() == null){return null;}

        //automatically set user to current user
        if(etapeDto.getType().equals("transmission"))
            etapeDto.setTransmisParId(userRepository.findByLoginOrEmail(SecurityUtils.getCurrentUserLogin()).getId());

        else if(etapeDto.getType().equals("reception"))
            etapeDto.setRecuParId(userRepository.findByLoginOrEmail(SecurityUtils.getCurrentUserLogin()).getId());

        return etapeService.save(etapeDto);
    }

    @GetMapping("/api/etapes-by-courrier/{courrierId}")
    public List<EtapeDto> getEtapesByCourrier(@PathVariable Long courrierId) {
        log.debug("REST request to get all etapes");
        return etapeService.findByCourrier(courrierId);
    }


    @GetMapping("/api/etapes-by-structure/{structureId}")
    public List<EtapeDto> getEtapesByStructure(@PathVariable Long structureId) {
        log.debug("REST request to get all etapes");
        return etapeService.findByStructure(structureId);
    }

    @GetMapping("/api/etapes-transmissions-by-current-user-structure")
    public List<EtapeDto> getTransmissionsByStructure() {
        log.debug("Request to get all transmissions by current user structure");
        return etapeService.findTransmissionsByCurrentUserStructure();
    }

    @GetMapping("/api/etapes-transmissions-by-current-user")
    public List<EtapeDto> getTransmissionsByCurrentUser() {
        log.debug("Request to get all transmissions by current user");
        return etapeService.findTransmissionsByCurrentUser();
    }

    @GetMapping("/api/etapes-receptions-by-current-user-structure")
    public List<EtapeDto> getReceptionsByStructure() {
        log.debug("Request to get all receptions by current user structure");
        return etapeService.findReceptionsAndEnregistrementsByCurrentUserStructure();
    }

    @GetMapping("/api/etapes-receptions-by-current-user")
    public List<EtapeDto> getReceptionsByCurrentUser() {
        log.debug("Request to get all receptions by current user structure");
        return etapeService.findReceptionsByCurrentUser();
    }

    /*@GetMapping("/api/etapes-by-courrier/{courrierId}")
    public List<EtapeDto> getEtapesByCourrier(@PathVariable Long courrierId) {
        log.debug("REST request to get all etapes");
        return etapeService.findByCourrier(courrierId);
    }*/

    /**
     * GET  /etapes/:id : get the "id" etape.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the etape, or with status 404 (Not Found)
     */
    @GetMapping("/api/etapes/{etapeId}")
    public ResponseEntity<EtapeDto> getEtape(@PathVariable Long etapeId) {
        log.debug("REST request to get Etape : {}");
        EtapeDto etapeDto = etapeService.findOne(etapeId);
        return Optional.ofNullable(etapeDto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * DELETE  /etapes/:id : delete the "id" etape.
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api/etapes/{etapeId}")
    public ResponseEntity<?> deleteEtape(@PathVariable Long etapeId) {
        log.debug("REST request to delete Etape : {}");
        try {
            etapeService.delete(etapeId);
        } catch (Exception e) {
            return new ResponseEntity(new CustomErrorType("Impossible d'annuler cette opération !"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<EtapeDto>(HttpStatus.NO_CONTENT);
    }
}
