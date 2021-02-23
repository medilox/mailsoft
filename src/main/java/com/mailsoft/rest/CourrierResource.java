package com.mailsoft.rest;

import com.mailsoft.dto.CourrierDto;
import com.mailsoft.repository.UserRepository;
import com.mailsoft.security.SecurityUtils;
import com.mailsoft.service.CourrierService;
import com.mailsoft.utils.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Courrier.
 */
@CrossOrigin
@RestController
public class CourrierResource {

    private final Logger log = LoggerFactory.getLogger(CourrierResource.class);

    @Autowired
    private CourrierService courrierService;

    @Autowired
    private UserRepository userRepository;

    /**
     * POST  /courriers : Create a new courrier.
     *
     * @param courrierDto the courrier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courrier, or with status 400 (Bad Request) if the courrier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api/courriers")
    public ResponseEntity<CourrierDto> createCourrier(@Valid @RequestBody CourrierDto courrierDto) {
        log.debug("REST request to save Courrier : {}", courrierDto);
        if (courrierDto.getId() != null) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A courrier with id " +
                    courrierDto.getId() + " already exist."), HttpStatus.CONFLICT);
        }
        //automatically set user to current user
        courrierDto.setRecuParId(userRepository.findByLoginOrEmail(SecurityUtils.getCurrentUserLogin()).getId());
        return courrierService.save(courrierDto);
    }

    /**
     * PUT  /courriers : Updates an existing courrier.
     *
     * @param courrierDto the courrier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated courrier,
     * or with status 400 (Bad Request) if the courrier is not valid,
     * or with status 500 (Internal Server Error) if the courrier couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/api/courriers")
    public ResponseEntity<CourrierDto> updateCourrier(@Valid @RequestBody CourrierDto courrierDto) {
        log.debug("REST request to update Courrier : {}", courrierDto);
        if (courrierDto.getId() == null) {
            return createCourrier(courrierDto);
        }
        return courrierService.update(courrierDto);
    }

    /**
     * GET  /courriers : get all the courriers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of courriers in body
     */
    @GetMapping("/api/courriers")
    public List<CourrierDto> getAllCourriers() {
        log.debug("REST request to get list of Courriers");
        return courrierService.findAll();
    }


    /**
     * GET  /courriers/:id : get the "id" courrier.
     *
     * @param id the id of the courrier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courrier, or with status 404 (Not Found)
     */
    @GetMapping("/api/courriers/{id}")
    public ResponseEntity<CourrierDto> getCourrier(@PathVariable Long id) {
        log.debug("REST request to get Courrier : {}", id);
        CourrierDto courrierDto = courrierService.findOne(id);

        return Optional.ofNullable(courrierDto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * DELETE  /courriers/:id : delete the "id" courrier.
     *
     * @param id the id of the courrier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api/courriers/{id}")
    public ResponseEntity<?> deleteCourrier(@PathVariable Long id) {
        log.debug("REST request to delete Courrier : {}", id);
        courrierService.delete(id);
        return new ResponseEntity<CourrierDto>(HttpStatus.NO_CONTENT);
    }

}
