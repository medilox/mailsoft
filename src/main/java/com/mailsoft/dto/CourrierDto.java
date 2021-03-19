package com.mailsoft.dto;

import com.mailsoft.domain.Courrier;
import com.mailsoft.domain.Etape;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by medilox on 3/10/17.
 */
@Data
public class CourrierDto{

    private Long id;
    private String refCourrier;
    private String initiateur;
    private String objet;
    private String dateEnvoi;
    private String dateReception;
    private String natureCourrier;
    private String concernes;
    private Long userId;
    private String user;
    //private List<EtapeDto> parcours;


    public CourrierDto createDTO(Courrier courrier) {
        CourrierDto courrierDto = new CourrierDto();

        if(courrier != null){
            courrierDto.setId(courrier.getId());
            courrierDto.setRefCourrier(courrier.getRefCourrier());
            courrierDto.setInitiateur(courrier.getInitiateur());
            courrierDto.setObjet(courrier.getObjet());
            courrierDto.setDateEnvoi(courrier.getDateEnvoi());
            courrierDto.setDateReception(courrier.getDateReception());
            courrierDto.setNatureCourrier(courrier.getNatureCourrier().toValue());
            courrierDto.setConcernes(courrier.getConcernes());

            if(courrier.getUser() != null){
                courrierDto.setUserId(courrier.getUser().getId());
                courrierDto.setUser(courrier.getUser().getFullName());
            }


            /*List<EtapeDto> parcours = new ArrayList<>();

            for (Etape etape: courrier.getParcours()) {
                parcours.add(new EtapeDto().createDTO(etape));
            }
            courrierDto.setParcours(parcours);*/
        }
        return courrierDto;
    }

}
