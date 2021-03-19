/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mailsoft.domain;

import com.mailsoft.domain.enumerations.NatureCourrier;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author medilox
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Courrier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "refCourrier")
    private String refCourrier;

    @Column(name = "initiateur")
    private String initiateur;

    @Column(name = "objet")
    private String objet;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "natureCourrier")
    private String natureCourrier;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "dateEnvoi")
    private LocalDate dateEnvoi;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "dateReception")
    private LocalDate dateReception;

    @Column(name = "concernes")
    private String concernes;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "created_date")
    private LocalDateTime createdDate;


    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courrier")
    private List<Etape> parcours = new ArrayList<>();

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(unique = true)
    private Etape lastEtape;

    public String getDateEnvoi() {
        String pattern = "yyyy-MM-dd";
        if(dateEnvoi != null) {
            return dateEnvoi.toString(pattern);
        }
        return null;
    }

    public void setDateEnvoi(String dateEnvoi) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate cd = null;
        if (dateEnvoi != null && !dateEnvoi.trim().isEmpty())
            cd = formatter.parseLocalDate(dateEnvoi);
        this.dateEnvoi = cd;
    }

    public String getDateReception() {
        String pattern = "yyyy-MM-dd";
        if(dateReception != null) {
            return dateReception.toString(pattern);
        }
        return null;
    }

    public void setDateReception(String dateReception) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate cd = null;
        if(dateReception!=null && !dateReception.isEmpty())
            cd = formatter.parseLocalDate(dateReception);
        this.dateReception = cd;
    }

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

    @Transient
    public NatureCourrier getNatureCourrier() {
        return NatureCourrier.fromValue(natureCourrier);
    }

    public void setNatureCourrier(NatureCourrier natureCourrier) {
        this.natureCourrier = natureCourrier.toValue();
    }
}
