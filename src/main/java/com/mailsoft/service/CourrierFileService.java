package com.mailsoft.service;

import com.mailsoft.domain.Courrier;
import com.mailsoft.domain.CourrierFile;
import com.mailsoft.domain.User;
import com.mailsoft.dto.CourrierFileDto;
import com.mailsoft.repository.CourrierFileRepository;
import com.mailsoft.repository.CourrierRepository;
import com.mailsoft.repository.UserRepository;
import com.mailsoft.utils.CustomErrorType;
import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Service Implementation for managing CourrierFile.
 */
@Service
@Transactional
public class CourrierFileService {

    private final Logger log = LoggerFactory.getLogger(CourrierFileService.class);

    @Autowired
    private CourrierFileRepository courrierFileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourrierRepository courrierRepository;

    @Value("${dir.courriers}")
    private String IMG_FOLDER;


    /**
     * Save a courrierFile.
     *
     * @param courrierFileDto the entity to save
     * @return the persisted entity
     */
    public ResponseEntity<CourrierFileDto> save(CourrierFileDto courrierFileDto,
                                                MultipartFile file) throws IOException {

        log.debug("Request to save CourrierFile : {}", courrierFileDto);

        CourrierFile courrierFile = new CourrierFile();

        courrierFile.setId(courrierFileDto.getId());
        courrierFile.setName(courrierFileDto.getName());
        courrierFile.setDescription(courrierFileDto.getDescription());

        //set created date;
        String pattern = "yyyy-MM-dd HH:mm";
        LocalDateTime date = new LocalDateTime();
        courrierFile.setCreatedDate(date.toString(pattern));

        if(courrierFileDto.getUserId() != null){
            User user = userRepository.findOne(courrierFileDto.getUserId());
            courrierFile.setUser(user);
        }

        if(courrierFileDto.getCourrierId() != null){
            Courrier courrier = courrierRepository.findOne(courrierFileDto.getCourrierId());
            courrierFile.setCourrier(courrier);
        }


        CourrierFile result = courrierFileRepository.save(courrierFile);

        if(result != null){
            if (!Files.exists(Paths.get(IMG_FOLDER))) {
                File courrierFiles = new File(IMG_FOLDER);
                if(! courrierFiles.mkdirs()) {
                    return new ResponseEntity(new CustomErrorType("Unable to create folder ${dir.courriers}"), HttpStatus.CONFLICT);
                }
            }

            if(file != null){
                if(!file.isEmpty()){
                    try {
                        file.transferTo(new File(IMG_FOLDER + result.getId()));
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                        return new ResponseEntity(new CustomErrorType("Error while saving courrierFile image"), HttpStatus.NO_CONTENT);
                    }
                }
            }
        }
        return new ResponseEntity<CourrierFileDto>(new CourrierFileDto().createDTO(result), HttpStatus.CREATED);
    }


    @Transactional(readOnly = true)
    public List<CourrierFileDto> findByCourrierId(Long courrierId) {
        log.debug("Request to get CourrierFiles by user");

        List<CourrierFileDto> courrierFileDtos = new ArrayList<>();
        List<CourrierFile> courrierFiles = courrierFileRepository.findByCourrierId(courrierId);

        for (CourrierFile courrierFile : courrierFiles)
            courrierFileDtos.add(new CourrierFileDto().createDTO(courrierFile));

        return courrierFileDtos;
    }

    /**
     *  Get one courrierFile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CourrierFileDto findOne(Long id) {
        log.debug("Request to get CourrierFile : {}", id);
        CourrierFile courrierFile = courrierFileRepository.findOne(id);
        return new CourrierFileDto().createDTO(courrierFile);
    }

    /**
     *  Delete the  courrierFile by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CourrierFile : {}", id);
        CourrierFile courrierFile = courrierFileRepository.findOne(id);

        if(Optional.ofNullable(courrierFile).isPresent()){
            courrierFileRepository.delete(id);
        }
    }

    public byte[] getCourrierFile(Long courrierFileId) throws IOException {
        File f = new File(IMG_FOLDER + courrierFileId);
        if(f.exists() && !f.isDirectory()) {
            return IOUtils.toByteArray(new FileInputStream(f));
        }
        return null;
    }
}