package com.academy.spring.datajpa.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "authors")
@ToString
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class Autore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String nome;
    @Column(name = "surname")
    private String cognome;
    @Column(name = "date_of_birth")
    private Date dataDiNascita;

    @OneToMany(mappedBy = "autore", fetch = FetchType.LAZY)
    private List<Tutorial> listaTutorial;

    @CreatedDate
    @Column(name = "creation_date")
    private Timestamp creationDate;

    @LastModifiedDate
    @Column(name = "last_update")
    private Timestamp lastUpdated;

    @Version
    private int version;

    public Autore() {
    }

}
