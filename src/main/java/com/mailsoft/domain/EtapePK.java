/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mailsoft.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author medilox
 */
@Data
@NoArgsConstructor
@Embeddable
public class EtapePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "courrier_id")
    private long courrierId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "structure_id")
    private long structureId;

    public EtapePK(long courrierId, long structureId) {
        this.courrierId = courrierId;
        this.structureId = structureId;
    }
}
