package com.lucas.bueno.torre.de.controle.controllers.queryFilters;

import com.lucas.bueno.torre.de.controle.entities.Occurrence;
import com.lucas.bueno.torre.de.controle.entities.enums.OccurrenceClassification;
import com.lucas.bueno.torre.de.controle.entities.enums.StatesEnum;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import static com.lucas.bueno.torre.de.controle.specifications.OccurrencesSpec.*;

@Data
public class OccurrenceQueryFilter {

    private OccurrenceClassification classification;
    private StatesEnum state;
    private String city;
    private Integer occurrenceYear;
    private Boolean hasFatalities;
    private String vehicleType;
    private String engineType;
    private String engineQuantity;
    private String damageLevel;

    public Specification<Occurrence> toSpecification() {
        return occurrenceClassificationEqual(classification)
                .and(stateEqual(state))
                .and(cityEqual(city))
                .and(occurrenceDateYearEquals(occurrenceYear))
                .and(hasFatalities(hasFatalities))
                .and(vehicleTypeEquals(vehicleType))
                .and(engineQuantityEquals(engineQuantity))
                .and(engineTypeEquals(engineType))
                .and(damageLevelEquals(damageLevel));
    }

}
