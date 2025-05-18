package com.lucas.bueno.torre.de.controle.entities;

import com.lucas.bueno.torre.de.controle.entities.enums.CountryEnum;
import com.lucas.bueno.torre.de.controle.entities.enums.InvestigationStatusEnum;
import com.lucas.bueno.torre.de.controle.entities.enums.OccurrenceClassification;
import com.lucas.bueno.torre.de.controle.entities.enums.StatesEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "occurrence_db")
public class Occurrence {


    @Id
    @Column(name = "codigo_ocorrencia")
    private Long occurrenceCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "ocorrencia_classificacao")
    private OccurrenceClassification occurrenceClassification;

    @Column(name = "ocorrencia_latitude")
    private String occurrenceLatitude;

    @Column(name = "ocorrencia_longitude")
    private String occurrenceLongitude;

    @Column(name = "ocorrencia_cidade")
    private String occurrenceCity;

    @Enumerated(EnumType.STRING)
    @Column(name = "ocorrencia_uf")
    private StatesEnum occurrenceState;

    @Enumerated(EnumType.STRING)
    @Column(name = "ocorrencia_pais")
    private CountryEnum occurrenceCountry;

    @Column(name = "ocorrencia_aerodromo")
    private String occurrenceAerodrome;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "ocorrencia_dia")
    private LocalDate occurrenceDate;

    @DateTimeFormat(pattern = "HH:mm:ss")
    @Column(name = "ocorrencia_hora")
    private LocalTime occurrenceTime;

    @Column(name = "investigacao_aeronave_liberada")
    private String investigationAircraftCleared;

    @Enumerated(EnumType.STRING)
    @Column(name = "investigacao_status")
    private InvestigationStatusEnum investigationStatus;

    @Column(name = "divulgacao_relatorio_numero")
    private String disclosureReportNumber;

    @Column(name = "divulgacao_relatorio_publicado")
    private String disclosureReportPublished;

    @Column(name = "divulgacao_dia_publicacao")
    private String disclosurePublicationDay;

    @Column(name = "total_recomendacoes")
    private Integer totalRecommendations;

    @Column(name = "total_aeronaves_envolvidas")
    private Integer totalAircraftInvolved;

    @Column(name = "ocorrencia_saida_pista")
    private String occurrenceRunwayDeparture;

    @OneToMany(mappedBy = "occurrence", cascade = CascadeType.ALL)
    private List<OccurrenceType> occurrenceType = new ArrayList<>();

    @OneToMany(mappedBy = "occurrence", cascade = CascadeType.ALL)
    private List<Aircraft> aircrafts = new ArrayList<>();

    @OneToMany(mappedBy = "occurrence", cascade = CascadeType.ALL)
    private List<ContributingFactor> contributingFactors = new ArrayList<>();
}


