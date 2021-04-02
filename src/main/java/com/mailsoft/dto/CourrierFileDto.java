package com.mailsoft.dto;

import com.mailsoft.domain.CourrierFile;
import lombok.Data;

/**
 * Created by medilox on 30/09/18.
 */
@Data
public class CourrierFileDto {

    private Long id;
    private String name;
    private String description;
    private String createdDate;
    private Long courrierId;
    private Long userId;
    private String user;

    public CourrierFileDto createDTO(CourrierFile courrierFile) {
        CourrierFileDto courrierFileDto = new CourrierFileDto();
        if(courrierFile != null){
            courrierFileDto.setId(courrierFile.getId());
            courrierFileDto.setCreatedDate(courrierFile.getCreatedDate());
            courrierFileDto.setName(courrierFile.getName());
            courrierFileDto.setDescription(courrierFile.getDescription());

            if(courrierFile.getCourrier() != null){
                courrierFileDto.setCourrierId(courrierFile.getCourrier().getId());
            }
            if(courrierFile.getUser() != null){
                courrierFileDto.setUserId(courrierFile.getUser().getId());
                courrierFileDto.setUser(courrierFile.getUser().getLogin());
            }
        }
        return courrierFileDto;
    }
}
