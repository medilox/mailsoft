package com.mailsoft.dto;

import com.mailsoft.domain.Structure;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by medilox on 3/10/17.
 */
@Data
public class StructureDto{

    private Long id;
    private String name;
    private String sigle;
    private Long parentId;
    private List<StructureDto> children;

    private Boolean leaf = false;
    private Boolean root = false;

    public StructureDto createDTO(Structure structure) {
        StructureDto structureDto = new StructureDto();

        if(structure != null){
            structureDto.setId(structure.getId());
            structureDto.setName(structure.getName());
            structureDto.setSigle(structure.getSigle());

            List<StructureDto> childrenDto = new ArrayList<>();
            if (structure.getChildren() != null) {
                for(Structure child : structure.getChildren()){
                    childrenDto.add(new StructureDto().createDTO(child));
                }

                structureDto.setChildren(childrenDto);

                if(childrenDto.isEmpty())
                    structureDto.setLeaf(true);
            }

            if(structure.getParent() != null){
                structureDto.setParentId(structure.getParent().getId());
            }
            else{
                structureDto.setRoot(true);
            }

        }
        return structureDto;
    }

}
