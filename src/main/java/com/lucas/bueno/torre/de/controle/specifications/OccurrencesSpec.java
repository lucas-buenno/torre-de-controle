package com.lucas.bueno.torre.de.controle.specifications;

import com.lucas.bueno.torre.de.controle.entities.Occurrence;
import com.lucas.bueno.torre.de.controle.entities.enums.DamageLevelEnum;
import com.lucas.bueno.torre.de.controle.entities.enums.OccurrenceClassification;
import com.lucas.bueno.torre.de.controle.entities.enums.StatesEnum;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;


@Slf4j
public class OccurrencesSpec {

    private static final Integer ZERO_FATALITIES = 0;

    public static Specification<Occurrence> occurrenceClassificationEqual(OccurrenceClassification classification) {
        return (root, query, builder) -> {

            if (classification == null) {
                log.debug("Nenhuma classificação de ocorrência filtrada");
                return builder.conjunction();
            }
            log.debug("Filtrando por classificação {}", classification);
            return builder.equal(root.get("occurrenceClassification"), classification);
        };
    }

    public static Specification<Occurrence> cityEqual(String occurrenceCity) {
        return (root, query, builder) -> {
            if (occurrenceCity == null) {
                log.debug("Nenhum filtro por cidade");
                return builder.conjunction();
            }
            log.debug("Filtrando por cidade: {}", occurrenceCity);
            return builder.equal(builder.upper(root.get("occurrenceCity")), occurrenceCity.toUpperCase());
        };
    }

    public static Specification<Occurrence> stateEqual(StatesEnum occurrenceState) {
        return (root, query, builder) -> {
            if (occurrenceState == null) {
                log.debug("Nenhum filtro por estado");
                return builder.conjunction();
            }
            log.debug("Filtrando por estado: {}", occurrenceState);
            return builder.equal(root.get("occurrenceState"), occurrenceState);
        };
    }

    public static Specification<Occurrence> occurrenceDateYearEquals(Integer occurrenceYear) {
        return (root, query, builder) -> {
            if (occurrenceYear == null) {
                log.debug("Nenhum filtro por ano de ocorrência");
                return builder.conjunction();
            }

            LocalDate startDate = LocalDate.of(occurrenceYear, 1, 1);
            LocalDate endDate = LocalDate.of(occurrenceYear, 12, 31);

            log.debug("Filtrando por ocorrências entre as datas {} e {}", startDate, endDate);
            return builder.between(root.get("occurrenceDate"),startDate, endDate);
        };
    }

    public static Specification<Occurrence> hasFatalities(Boolean hasFatalities) {
        return (root, query, builder) -> {
            if (hasFatalities == null) {
                log.debug("Nenhum filtro de fatalidades");
                return builder.conjunction();

            } else if (!hasFatalities) {
                log.debug("Filtrando por ocorrências SEM fatalidades: hasFatalities:{}", hasFatalities);
                return builder.equal(root.join("aircrafts").get("totalFatalities"), ZERO_FATALITIES);
            }
            log.debug("Filtrando por ocorrências COM fatalidades: hasFatalities:{}", hasFatalities);
            return builder.greaterThan(root.join("aircrafts").get("totalFatalities"), ZERO_FATALITIES);
        };
    }

    public static Specification<Occurrence> vehicleTypeEquals(List<String> vehicleType) {
        return (root, query, builder) -> {
            if (vehicleType == null) {
                log.debug("Nenhum filtro de tipo veículo");
                return builder.conjunction();
            }

            log.debug("Filtrando pelo tipo de veículo: {}", vehicleType);

            return root.join("aircrafts").get("vehicleType").in(vehicleType);
        };
    }

    public static Specification<Occurrence> engineTypeEquals(String engineType) {
        return (root, query, builder) -> {
            if (engineType == null) {
                log.debug("Nenhum filtro de tipo d emotor");
                return builder.conjunction();
            }
            log.debug("Filtrando pelo tipo de motor: {}", engineType);
            return builder.equal(builder.upper(
                    root.join("aircrafts").get("engineQuantity")),
                    engineType.toUpperCase()
            );
        };
    }

    public static Specification<Occurrence> engineQuantityEquals(String engineQuantity) {
        return (root, query, builder) -> {
            if (engineQuantity == null) {
                log.debug("Nenhum filtro de quantidade de motor");
                return builder.conjunction();
            }
            log.debug("Filtrando pela quantidade de motor: {}", engineQuantity);
            return builder.equal(builder.upper(
                    root.join("aircrafts").get("engineQuantity")),
                    engineQuantity.toUpperCase()
            );
        };
    }

    public static Specification<Occurrence> damageLevelEquals(DamageLevelEnum damageLevel) {
        return (root, query, builder) -> {
            if (damageLevel == null) {
                log.debug("Nenhum filtro de nível de dano");
                return builder.conjunction();
            }
            log.debug("Filtrando pelo tipo de dano {}", damageLevel);
            return builder.equal(root.join("aircrafts").get("damageLevel"), damageLevel
            );
        };
    }


}
