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

import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author medilox
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(unique = true)
    private String email;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "password")
    private String password;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "activated")
    private Boolean activated;

    @Size(min = 2, max = 5)
    @Column(name = "lang_key", length = 5)
    private String langKey;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "reset_date", nullable = true)
    private LocalDateTime resetDate = null;

    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Role role;

    @Size(max = 100)
    @Column(name = "nom")
    private String nom;

    @Size(max = 100)
    @Column(name = "prenom")
    private String prenom;

    @Size(max = 45)
    @Column(name = "telephone")
    private String telephone;

    @Column(name = "credentialsSent")
    private Boolean credentialsSent;

    @JoinColumn(name = "structure_id", referencedColumnName = "id")
    @ManyToOne
    private Structure structure;

    public String getCreatedDate() {
        String pattern = "yyyy-MM-dd";
        if(createdDate != null) {
            return createdDate.toString(pattern);
        }
        return null;
    }

    public void setCreatedDate(String createdDate) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate cd = null;
        if(createdDate!=null && !createdDate.isEmpty())
            cd = formatter.parseLocalDate(createdDate);
        this.createdDate = cd;
    }

    public String getResetDate() {
        String pattern = "yyyy-MM-dd HH:mm";
        if(resetDate != null) {
            return resetDate.toString(pattern);
        }
        return null;
    }

    public void setResetDate(String resetDate) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        LocalDateTime rd = null;
        if(resetDate!=null)
            rd = LocalDateTime.parse(resetDate, formatter);
        this.resetDate = rd;
    }

    public String getFullName() {
        String name = "";
        if(this.nom != null)
            name += this.nom;

        if(this.prenom != null)
            name += " " + this.prenom;

        return name;
    }
}

