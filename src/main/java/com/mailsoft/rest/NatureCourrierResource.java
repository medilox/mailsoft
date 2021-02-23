package com.mailsoft.rest;

import com.mailsoft.domain.enumerations.NatureCourrier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by medilox on 11/7/17.
 */

@RestController
@CrossOrigin
@RequestMapping("/api")
public class NatureCourrierResource {

    @CrossOrigin
    @RequestMapping(value = "/natures-courrier", method = RequestMethod.GET)
    public List<NatureCourrierDto> getNatureCourriers( ) {
        List<NatureCourrierDto> result = new ArrayList<>();

        List<NatureCourrier> natureCourriers = Arrays.asList(NatureCourrier.values());

        for(NatureCourrier natureCourrier : natureCourriers){
            result.add(new NatureCourrierDto(natureCourrier.name(), natureCourrier.toValue()));
        }

        return result;
    }

    private class NatureCourrierDto {
        private String name;
        private String value;

        public NatureCourrierDto(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }
}
