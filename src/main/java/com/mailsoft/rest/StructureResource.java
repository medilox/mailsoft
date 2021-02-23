package com.mailsoft.rest;

import com.mailsoft.dto.StructureDto;
import com.mailsoft.repository.UserRepository;
import com.mailsoft.service.StructureService;
import com.mailsoft.utils.CustomErrorType;
import com.mailsoft.service.StructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Structure.
 */
@CrossOrigin
@RestController
public class StructureResource {

    private final Logger log = LoggerFactory.getLogger(StructureResource.class);

    @Autowired
    private StructureService structureService;

    @Autowired
    private UserRepository userRepository;

    /**
     * POST  /structures : Create a new structure.
     *
     * @param structureDto the structure to create
     * @return the ResponseEntity with status 201 (Created) and with body the new structure, or with status 400 (Bad Request) if the structure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api/structures")
    public ResponseEntity<StructureDto> createStructure(@Valid @RequestBody StructureDto structureDto) {
        log.debug("REST request to save Structure : {}", structureDto);
        if (structureDto.getId() != null) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A structure with id " +
                    structureDto.getId() + " already exist."), HttpStatus.CONFLICT);
        }
        return structureService.save(structureDto);
    }

    /**
     * PUT  /structures : Updates an existing structure.
     *
     * @param structureDto the structure to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated structure,
     * or with status 400 (Bad Request) if the structure is not valid,
     * or with status 500 (Internal Server Error) if the structure couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/api/structures")
    public ResponseEntity<StructureDto> updateStructure(@Valid @RequestBody StructureDto structureDto) {
        log.debug("REST request to update Structure : {}", structureDto);
        if (structureDto.getId() == null) {
            return createStructure(structureDto);
        }
        return structureService.update(structureDto);
    }

    /**
     * GET  /structures : get all the structures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of structures in body
     */
    @GetMapping("/api/structures")
    public List<StructureDto> getAllStructures() {
        log.debug("REST request to get list of Structures");
        return structureService.findAll();
    }


    /**
     * GET  /structures/:id : get the "id" structure.
     *
     * @param id the id of the structure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the structure, or with status 404 (Not Found)
     */
    @GetMapping("/api/structures/{id}")
    public ResponseEntity<StructureDto> getStructure(@PathVariable Long id) {
        log.debug("REST request to get Structure : {}", id);
        StructureDto structureDto = structureService.findOne(id);

        return Optional.ofNullable(structureDto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * DELETE  /structures/:id : delete the "id" structure.
     *
     * @param id the id of the structure to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api/structures/{id}")
    public ResponseEntity<?> deleteStructure(@PathVariable Long id) {
        log.debug("REST request to delete Structure : {}", id);
        structureService.delete(id);
        return new ResponseEntity<StructureDto>(HttpStatus.NO_CONTENT);
    }

}
