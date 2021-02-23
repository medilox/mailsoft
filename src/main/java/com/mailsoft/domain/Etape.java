/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mailsoft.domain;

import lombok.*;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author medilox
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "etape")
public class Etape {

    @EmbeddedId
    protected EtapePK etapePK;

    @JoinColumn(name = "structure_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Structure structure;

    @JoinColumn(name = "courrier_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Courrier courrier;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "dateEntree")
    private LocalDate dateEntree;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "dateSortie")
    private LocalDate dateSortie;

    public Etape(EtapePK etapePK) {
        this.etapePK = etapePK;
    }

    public Etape(long courrierId, long structureId) {
        this.etapePK = new EtapePK(courrierId, structureId);
    }

    public String getDateEntree() {
        String pattern = "ddMMyyyy";
        if(dateEntree != null) {
            return dateEntree.toString(pattern);
        }
        return null;
    }

    public void setDateEntree(String dateEntree) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMyyyy");
        LocalDate cd = null;
        if (dateEntree != null && !dateEntree.trim().isEmpty())
            cd = formatter.parseLocalDate(dateEntree);
        this.dateEntree = cd;
    }

    public String getDateSortie() {
        String pattern = "ddMMyyyy";
        if(dateSortie != null) {
            return dateSortie.toString(pattern);
        }
        return null;
    }

    public void setDateSortie(String dateSortie) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMyyyy");
        LocalDate cd = null;
        if(dateSortie!=null && !dateSortie.isEmpty())
            cd = formatter.parseLocalDate(dateSortie);
        this.dateSortie = cd;
    }

}
