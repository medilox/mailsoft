/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mailsoft.domain;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;
import javax.validation.constraints.Size;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "structure_id")
    @ManyToOne(optional = false)
    private Structure structure;

    @JoinColumn(name = "courrier_id")
    @ManyToOne(optional = false)
    private Courrier courrier;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @JoinColumn(name = "transmis_par_id", referencedColumnName = "id")
    @ManyToOne
    private User transmisPar;

    @JoinColumn(name = "recu_par_id", referencedColumnName = "id")
    @ManyToOne
    private User recuPar;

    @JoinColumn(name = "enregistre_par_id", referencedColumnName = "id")
    @ManyToOne
    private User enregistrePar;

    @Column(name = "instructions")
    private String instructions;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(unique = true)
    private Etape previous;

    public String getCreatedDate() {
        String pattern = "yyyy-MM-dd HH:mm";
        if(createdDate != null) {
            return createdDate.toString(pattern);
        }
        return null;
    }

    public void setCreatedDate(String createdDate) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        LocalDateTime cd = null;
        if(createdDate!=null)
            cd = LocalDateTime.parse(createdDate, formatter);
        this.createdDate = cd;
    }

}
