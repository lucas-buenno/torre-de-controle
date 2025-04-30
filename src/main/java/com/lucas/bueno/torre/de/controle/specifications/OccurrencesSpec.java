package com.lucas.bueno.torre.de.controle.specifications;

import com.lucas.bueno.torre.de.controle.entities.Occurrence;
import com.lucas.bueno.torre.de.controle.entities.enums.OccurrenceClassification;
import com.lucas.bueno.torre.de.controle.entities.enums.StatesEnum;


import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;



public class OccurrencesSpec {


    public static Specification<Occurrence> occurrenceClassificationEqual(OccurrenceClassification classification) {
        return (root, query, builder) -> {

            if (classification == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("occurrenceClassification"), classification);
        };
    }

    public static Specification<Occurrence> cityEqual(String occurrenceCity) {
        return (root, query, builder) -> {
            if (occurrenceCity == null) {
                return builder.conjunction();
            }
            return builder.equal(builder.upper(root.get("occurrenceCity")), occurrenceCity.toUpperCase());
        };
    }

    public static Specification<Occurrence> stateEqual(StatesEnum occurrenceState) {
        return (root, query, builder) -> {
            if (occurrenceState == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("occurrenceState"), occurrenceState);
        };
    }

    public static Specification<Occurrence> occurrenceDateYearEquals(Integer occurrenceYear) {
        return (root, query, builder) -> {
            if (occurrenceYear == null) {
                return builder.conjunction();
            }

            LocalDate startDate = LocalDate.of(occurrenceYear, 1, 1);
            LocalDate endDate = LocalDate.of(occurrenceYear, 12, 31);
            return builder.between(root.get("occurrenceDate"),startDate, endDate);
        };
    }

    public static Specification<Occurrence> hasFatalities(Boolean hasFatalities) {
        return (root, query, builder) -> {
            if (hasFatalities == null) {
                return builder.conjunction();

            } else if (!hasFatalities) {
                return builder.equal(root.join("aircrafts").get("totalFatalities"), 0);
            }

            return builder.greaterThan(root.join("aircrafts").get("totalFatalities"), 0);
        };
    }

    public static Specification<Occurrence> vehicleTypeEquals(String vehicleType) {
        return (root, query, builder) -> {
            if (vehicleType == null) {
                return builder.conjunction();
            }
            return builder.equal(builder.upper(
                    root.join("aircrafts").get("vehicleType")),
                    vehicleType.toUpperCase()
            );
        };
    }

    public static Specification<Occurrence> engineTypeEquals(String engineType) {
        return (root, query, builder) -> {
            if (engineType == null) {
                return builder.conjunction();
            }
            return builder.equal(builder.upper(
                    root.join("aircrafts").get("engineQuantity")),
                    engineType.toUpperCase()
            );
        };
    }

    public static Specification<Occurrence> engineQuantityEquals(String engineQuantity) {
        return (root, query, builder) -> {
            if (engineQuantity == null) {
                return builder.conjunction();
            }
            return builder.equal(builder.upper(
                    root.join("aircrafts").get("engineQuantity")),
                    engineQuantity.toUpperCase()
            );
        };
    }

    public static Specification<Occurrence> damageLevelEquals(String damageLevel) {
        return (root, query, builder) -> {
            if (damageLevel == null) {
                return builder.conjunction();
            }
            return builder.equal(builder.upper(
                    root.join("aircrafts").get("damageLevel")),
                    damageLevel.toUpperCase()
            );
        };
    }


}
