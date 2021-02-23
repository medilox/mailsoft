package com.mailsoft.domain;

import lombok.*;
import org.joda.time.LocalDate;
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
@Table(name = "structure")
public class Structure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "sigle")
    private String sigle;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    private List<Structure> children;

    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @ManyToOne
    private Structure parent;
}