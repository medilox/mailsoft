package com.mailsoft.dto;

import com.mailsoft.domain.Etape;
import lombok.Data;

/**
 * Created by medilox on 30/09/18.
 */
@Data
public class EtapeDto {

    private Long id;
    private String instructions;
    private Long structureId;
    private String structureSigle;
    private String structureName;
    private Long courrierId;
    private CourrierDto courrier;
    private String createdDate;
    private Long transmisParId;
    private String transmisPar;
    private Long recuParId;
    private String recuPar;
    private Long enregistreParId;
    private String enregistrePar;
    private String type;
    private EtapeDto previous;


    public EtapeDto createDTO(Etape etape) {
        EtapeDto etapeDto = new EtapeDto();
        if(etape != null){

            etapeDto.setId(etape.getId());
            etapeDto.setCreatedDate(etape.getCreatedDate());
            etapeDto.setInstructions(etape.getInstructions());

            if(etape.getStructure() != null){
                etapeDto.setStructureId(etape.getStructure().getId());
                etapeDto.setStructureSigle(etape.getStructure().getSigle());
                etapeDto.setStructureName(etape.getStructure().getName());
            }

            if(etape.getCourrier() != null){
                etapeDto.setCourrierId(etape.getCourrier().getId());
                etapeDto.setCourrier(new CourrierDto().createDTO(etape.getCourrier()));
            }

            if(etape.getTransmisPar() != null){
                etapeDto.setTransmisParId(etape.getTransmisPar().getId());
                etapeDto.setTransmisPar(etape.getTransmisPar().getLogin());
            }
            if(etape.getRecuPar() != null){
                etapeDto.setRecuParId(etape.getRecuPar().getId());
                etapeDto.setRecuPar(etape.getRecuPar().getLogin());
            }
            if(etape.getEnregistrePar() != null){
                etapeDto.setEnregistreParId(etape.getEnregistrePar().getId());
                etapeDto.setEnregistrePar(etape.getEnregistrePar().getLogin());
            }
            if(etape.getPrevious() != null){
                if(etape.getRecuPar() != null){
                    etapeDto.setTransmisParId(etape.getPrevious().getTransmisPar().getId());
                    etapeDto.setTransmisPar(etape.getPrevious().getTransmisPar().getLogin());
                }
                etapeDto.setPrevious(new EtapeDto().createDTO(etape.getPrevious()));
            }
            return etapeDto;
        }
        return null;
    }
}
