package com.mailsoft.rest;

import com.mailsoft.dto.CourrierFileDto;
import com.mailsoft.repository.CourrierFileRepository;
import com.mailsoft.repository.UserRepository;
import com.mailsoft.security.SecurityUtils;
import com.mailsoft.service.CourrierFileService;
import com.mailsoft.utils.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CourrierFile.
 */
@CrossOrigin
@RestController
public class CourrierFileResource {

    private final Logger log = LoggerFactory.getLogger(CourrierFileResource.class);

    @Autowired
    private CourrierFileService courrierFileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourrierFileRepository courrierFileRepository;

    /**
     * POST  /courrier-files : Create a new courrierFile.
     *
     * @param courrierFileDto the courrierFile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new courrierFile, or with status 400 (Bad Request) if the courrierFile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/api/courrier-files", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CourrierFileDto> createCourrierFile(@RequestPart("courrierFile") CourrierFileDto courrierFileDto,
                                                              @RequestPart(name="file", required=false) MultipartFile file) throws IOException {

        log.debug("REST request to save CourrierFile : {}", courrierFileDto);
        if (courrierFileDto.getId() != null) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A courrierFile with id " +
                    courrierFileDto.getId() + " already exist."), HttpStatus.CONFLICT);
        }

        //automatically set user to current user
        courrierFileDto.setUserId(userRepository.findByLogin(SecurityUtils.getCurrentUserLogin()).getId());

        return courrierFileService.save(courrierFileDto, file);
    }

    @GetMapping("/api/courrier-files-by-courrier/{courrierId}")
    public List<CourrierFileDto> getCourrierFilesByCourrierId(@PathVariable Long courrierId) {
        log.debug("REST request to get list of CourrierFiles by current user");
        return courrierFileService.findByCourrierId(courrierId);
    }
    

    /**
     * GET  /courrier-files/:id : get the "id" courrierFile.
     *
     * @param id the id of the courrierFile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the courrierFile, or with status 404 (Not Found)
     */
    @GetMapping("/api/courrier-files/{id}")
    public ResponseEntity<CourrierFileDto> getCourrierFile(@PathVariable Long id) {
        log.debug("REST request to get CourrierFile : {}", id);
        CourrierFileDto courrierFileDto = courrierFileService.findOne(id);

        return Optional.ofNullable(courrierFileDto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }



    /*
    @RequestMapping(value="/api/courrier-file-image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getCourrierFileImage(@PathVariable("id") Long courrierFileId) throws IOException {
        return courrierFileService.getCourrierFileImage(courrierFileId);
    }
    */

    @GetMapping("/api/download/{id}")
    public ResponseEntity<byte[]> downloadCourrierFile(@PathVariable("id") Long courrierFileId) throws IOException {

        CourrierFileDto courrierFileDto = courrierFileService.findOne(courrierFileId);

        HttpHeaders headers = new HttpHeaders();
        byte[] data = courrierFileService.getCourrierFile(courrierFileId);
        //headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        String filename = "unknown";
        if(courrierFileDto.getName() != null)
        filename = courrierFileDto.getName();

        headers.set(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=" + filename);
        //headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
        return response;
    }

    /**
     * DELETE  /courrier-files/:id : delete the "id" courrierFile.
     *
     * @param id the id of the courrierFile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api/courrier-files/{id}")
    public ResponseEntity<?> deleteCourrierFile(@PathVariable Long id) {
        log.debug("REST request to delete CourrierFile : {}", id);
        courrierFileService.delete(id);
        return new ResponseEntity<CourrierFileDto>(HttpStatus.NO_CONTENT);
    }

}
