package com.mailsoft.dto;

import com.mailsoft.domain.Etape;
import lombok.Data;

/**
 * Created by medilox on 30/09/18.
 */
@Data
public class EtapeDto {

    private Long structureId;;
    private Long courrierId;
    private String dateEntree;
    private String dateSortie;


    public EtapeDto createDTO(Etape etape) {
        EtapeDto etapeDto = new EtapeDto();
        if(etape != null){

            etapeDto.setDateEntree(etape.getDateEntree());
            etapeDto.setDateSortie(etape.getDateSortie());

            if(etape.getStructure() != null){
                etapeDto.setStructureId(etape.getStructure().getId());
            }

            if(etape.getCourrier() != null){
                etapeDto.setCourrierId(etape.getCourrier().getId());
            }

            return etapeDto;
        }
        return null;
    }
}
