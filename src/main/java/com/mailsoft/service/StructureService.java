package com.mailsoft.service;

import com.mailsoft.domain.Structure;
import com.mailsoft.domain.User;
import com.mailsoft.dto.StructureDto;
import com.mailsoft.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service Implementation for managing Structure.
 */
@Service
@Transactional
public class StructureService {

    private final Logger log = LoggerFactory.getLogger(StructureService.class);

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;


    /**
     * Save a structure.
     *
     * @param structureDto the entity to save
     * @return the persisted entity
     */
    public ResponseEntity<StructureDto> save(StructureDto structureDto) {

        log.debug("Request to save Structure : {}", structureDto);
        Structure structure = new Structure();
        structure.setId(structureDto.getId());

        structure.setName(structureDto.getName());
        structure.setSigle(structureDto.getSigle());

        if(structureDto.getParentId() != null){
            Structure parent = structureRepository.findOne(structureDto.getParentId());
            structure.setParent(parent);
        }

        Structure result = structureRepository.save(structure);
        return new ResponseEntity<StructureDto>(new StructureDto().createDTO(result), HttpStatus.CREATED);
    }


    public ResponseEntity<StructureDto> update(StructureDto structureDto) {
        log.debug("Request to save Structure : {}", structureDto);
        Structure structure = structureRepository.findOne(structureDto.getId());

        structure.setName(structureDto.getName());
        structure.setSigle(structureDto.getSigle());

        if(structureDto.getParentId() != null){
            Structure parent = structureRepository.findOne(structureDto.getParentId());
            structure.setParent(parent);
        }
        else{
            structure.setParent(null);
        }

        Structure result = structureRepository.save(structure);

        return new ResponseEntity<StructureDto>(new StructureDto().createDTO(result), HttpStatus.CREATED);
    }

    /**
     *  Get all the structures by user id.
     *
     *  @return the list of entities
     */
    public List<StructureDto> findAll(){
        log.debug("Request to get all Structures");

        List<Structure> structures = structureRepository.findAll();

        List<StructureDto> structureDtos = new ArrayList<>();

        for (Structure structure : structures)
            structureDtos.add(new StructureDto().createDTO(structure));

        return  structureDtos;
    }


    /**
     *  Get one structure by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public StructureDto findOne(Long id) {
        log.debug("Request to get Structure : {}", id);
        Structure structure = structureRepository.findOne(id);
        return new StructureDto().createDTO(structure);
    }

    /**
     *  Delete the  structure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Structure : {}", id);
        Structure structure = structureRepository.findOne(id);

        if(Optional.ofNullable(structure).isPresent()){
            structureRepository.delete(id);
        }
    }

}