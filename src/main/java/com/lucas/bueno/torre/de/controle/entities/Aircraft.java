package com.lucas.bueno.torre.de.controle.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "aircraft_db")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "codigo_ocorrencia", unique = true)
    private Long occurrenceCode;

    @Column(name = "aeronave_matricula")
    private String aircraftRegistration;

    @Column(name = "aeronave_operador_categoria")
    private String categoryOperator;

    @Column(name = "aeronave_tipo_veiculo")
    private String vehicleType;

    @Column(name = "aeronave_fabricante")
    private String manufacturer;

    @Column(name = "aeronave_modelo")
    private String model;

    @Column(name = "aeronave_tipo_icao")
    private String icaoType;

    @Column(name = "aeronave_motor_tipo")
    private String engineType;

    @Column(name = "aeronave_motor_quantidade")
    private String engineQuantity;

    @Column(name = "aeronave_pmd")
    private Long aircraftPmd;

    @Column(name = "aeronave_pmd_categoria")
    private Long aircraftPmdCategory;

    @Column(name = "aeronave_assentos")
    private Integer aircraftSeats;

    @Column(name = "aeronave_ano_fabricacao")
    private Integer fabricationYear;

    @Column(name = "aeronave_pais_fabricante")
    private String manufacturingCountry;

    @Column(name = "aeronave_pais_registro")
    private String registerCountry;

    @Column(name = "aeronave_registro_categoria")
    private String registerCategory;

    @Column(name = "aeronave_registro_segmento")
    private String registerSegment;

    @Column(name = "aeronave_voo_origem")
    private String originFlight;

    @Column(name = "aeronave_voo_destino")
    private String destinationFlight;

    @Column(name = "aeronave_fase_operacao")
    private String operationPhase;

    @Column(name = "aeronave_tipo_operacao")
    private String operationType;

    @Column(name = "aeronave_nivel_dano")
    private String damageLevel;

    @Column(name = "aeronave_fatalidades_total")
    private Integer totalFatalities;

    @ManyToOne
    @JoinColumn(name = "codigo_ocorrencia", insertable = false, updatable = false)
    private Occurrence occurrence;
}
