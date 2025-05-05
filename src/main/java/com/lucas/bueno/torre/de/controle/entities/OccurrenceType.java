package com.lucas.bueno.torre.de.controle.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "occurrence_type_db")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OccurrenceType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "codigo_ocorrencia", unique = true)
    private Long occurrenceCode;

    @Column(name = "ocorrencia_tipo")
    private String occurrenceType;

    @Column(name = "ocorrencia_tipo_categoria")
    private String occurrenceTypeCategory;

    @Column(name = "taxonomia_tipo_icao")
    private String taxonomyIcaoType;

    @ManyToOne
    @JoinColumn(name = "codigo_ocorrencia", insertable = false, updatable = false)
    private Occurrence occurrence;
}
