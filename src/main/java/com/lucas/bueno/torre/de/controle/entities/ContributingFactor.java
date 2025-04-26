package com.lucas.bueno.torre.de.controle.entities;

import com.lucas.bueno.torre.de.controle.entities.enums.AreaFactor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "contributing_factor_db")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ContributingFactor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "codigo_ocorrencia", unique = true)
    private Long occurrenceCode;

    @Column(name = "fator_nome")
    private String nameFactor;

    @Column(name = "fator_aspecto")
    private String aspectFactor;

    @Column(name = "fator_condicionante")
    private String conditioningFactor;

    @Column(name = "fator_area")
    private String areaFactor;

    @ManyToOne
    @JoinColumn(name = "codigo_ocorrencia", insertable = false, updatable = false)
    private Occurrence occurrence;
}
