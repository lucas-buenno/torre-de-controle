package com.lucas.bueno.torre.de.controle.services;

import com.lucas.bueno.torre.de.controle.controllers.dto.AircraftDTO;
import com.lucas.bueno.torre.de.controle.controllers.dto.ContributingFactorDTO;
import com.lucas.bueno.torre.de.controle.controllers.dto.OccurrenceResponseDTO;
import com.lucas.bueno.torre.de.controle.controllers.dto.OccurrenceTypeDTO;
import com.lucas.bueno.torre.de.controle.controllers.queryFilters.OccurrenceQueryFilter;
import com.lucas.bueno.torre.de.controle.entities.Aircraft;
import com.lucas.bueno.torre.de.controle.entities.ContributingFactor;
import com.lucas.bueno.torre.de.controle.entities.Occurrence;
import com.lucas.bueno.torre.de.controle.entities.OccurrenceType;
import com.lucas.bueno.torre.de.controle.entities.enums.*;
import com.lucas.bueno.torre.de.controle.exceptions.OccurrenceDoNotExists;
import com.lucas.bueno.torre.de.controle.repositories.OccurrenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OccurrenceServiceTest {


    @InjectMocks
    private OccurrenceService service;

    @Mock
    private OccurrenceRepository repository;

    private Occurrence dummyOccurrence = new Occurrence();
    private Aircraft dummyAircraft = new Aircraft();
    private ContributingFactor dummyContributingFactor = new ContributingFactor();
    private OccurrenceType dummyOccurrenceType = new OccurrenceType();
    private Page<Occurrence> mockPage;

    @Captor
    ArgumentCaptor<Specification<Occurrence>> specCaptor;


    @BeforeEach
    void setUp() {

        //Mock Aircraft
        dummyAircraft.setId(UUID.randomUUID());
        dummyAircraft.setOccurrenceCode(1L);
        dummyAircraft.setAircraftRegistration("PT-ABC");
        dummyAircraft.setCategoryOperator("Comercial");
        dummyAircraft.setVehicleType("Avião");
        dummyAircraft.setManufacturer("Mock Aircraft Co.");
        dummyAircraft.setModel("Mock Model");
        dummyAircraft.setIcaoType("B732");
        dummyAircraft.setEngineType("Type Engine");
        dummyAircraft.setEngineQuantity("2");
        dummyAircraft.setAircraftPmd(15000L);
        dummyAircraft.setAircraftPmdCategory(3L);
        dummyAircraft.setAircraftSeats(120);
        dummyAircraft.setFabricationYear(2005);
        dummyAircraft.setManufacturingCountry("Brasil");
        dummyAircraft.setRegisterCountry("Brasil");
        dummyAircraft.setRegisterCategory("Regular");
        dummyAircraft.setRegisterSegment("Transporte de Passageiros");
        dummyAircraft.setOriginFlight("SBGR");
        dummyAircraft.setDestinationFlight("SBSP");
        dummyAircraft.setOperationPhase("Cruzeiro");
        dummyAircraft.setOperationType("Regular");
        dummyAircraft.setDamageLevel(DamageLevelEnum.DESTRUIDA);
        dummyAircraft.setTotalFatalities(0);

        //ContributingFactor
        dummyContributingFactor.setId(UUID.randomUUID());
        dummyContributingFactor.setOccurrenceCode(1L);
        dummyContributingFactor.setNameFactor("Fator humano");
        dummyContributingFactor.setAspectFactor("Fadiga da tripulação");
        dummyContributingFactor.setConditioningFactor("Jornada prolongada");
        dummyContributingFactor.setAreaFactor("Operacional");

        //OccurrenceType
        dummyOccurrenceType.setId(UUID.randomUUID());
        dummyOccurrenceType.setOccurrenceCode(1L);
        dummyOccurrenceType.setOccurrenceType("Mock Type");
        dummyOccurrenceType.setOccurrenceTypeCategory("Mock category");
        dummyOccurrenceType.setTaxonomyIcaoType("Mock Icao");

        //Occurrence
        dummyOccurrence.setOccurrenceCode(1L);
        dummyOccurrence.setOccurrenceClassification(OccurrenceClassification.ACIDENTE);
        dummyOccurrence.setOccurrenceLatitude("-23.4321");
        dummyOccurrence.setOccurrenceLongitude("-46.5432");
        dummyOccurrence.setOccurrenceCity("São José dos Campos");
        dummyOccurrence.setOccurrenceState(StatesEnum.SP);
        dummyOccurrence.setOccurrenceCountry(CountryEnum.BRASIL);
        dummyOccurrence.setOccurrenceAerodrome("SBSJ");
        dummyOccurrence.setOccurrenceDate(LocalDate.of(2023, 5, 15));
        dummyOccurrence.setOccurrenceTime(LocalTime.of(14, 30, 0));
        dummyOccurrence.setInvestigationAircraftCleared("SIM");
        dummyOccurrence.setInvestigationStatus(InvestigationStatusEnum.FINALIZADA);
        dummyOccurrence.setDisclosureReportNumber("REL-2023-001");
        dummyOccurrence.setDisclosureReportPublished("SIM");
        dummyOccurrence.setDisclosurePublicationDay("15/08/2023");
        dummyOccurrence.setTotalAircraftInvolved(1);
        dummyOccurrence.setOccurrenceRunwayDeparture("NÃO");

        dummyOccurrence.setOccurrenceType(List.of(dummyOccurrenceType));
        dummyOccurrence.setAircrafts(List.of(dummyAircraft));
        dummyOccurrence.setContributingFactors(List.of(dummyContributingFactor));


        mockPage = new PageImpl<>(List.of(dummyOccurrence));
    }

    @Nested
    class getOccurrenceById {

        @Test
        @DisplayName("Should throw exception when occurrence does not exists")
        void shouldThrowExceptionWhenOccurrenceDoesNotExists() {

            //Arrange
            var id = 1L;
            doReturn(Optional.empty()).when(repository).findById(eq(id));

            //Act
            var exception = assertThrows(OccurrenceDoNotExists.class, ()-> {
                service.getOccurrenceById(id);
            });

            var expected = "Não existe ocorrência para o id " + id;

            //Assert
            verify(repository, times(1)).findById(eq(id));
            assertEquals(expected, exception.getMessage());
        }

        @Test
        @DisplayName("Should return a occurrence when exists")
        void shouldReturnAOccurrenceWhenExists() {

            //ARRANGE
            Long id = 1L;
            doReturn(Optional.of(dummyOccurrence)).when(repository).findById(eq(id));

            //ACT

            var output = service.getOccurrenceById(id);

            //ASSERT
            assertNotNull(output);
            assertEquals(dummyOccurrence.getOccurrenceClassification(), output.occurrenceClassification());
            assertEquals(dummyOccurrence.getOccurrenceState(), output.occurrenceState());
            assertEquals(dummyOccurrence.getDisclosureReportNumber(), output.disclosureReportNumber());
            assertEquals(dummyOccurrence.getInvestigationStatus(), output.investigationStatusEnum());

            assertEquals(dummyOccurrence.getOccurrenceLatitude(), output.occurrenceLatitude());
            assertEquals(dummyOccurrence.getOccurrenceLongitude(), output.occurrenceLongitude());
            assertEquals(dummyOccurrence.getOccurrenceCity(), output.occurrenceCity());
            assertEquals(dummyOccurrence.getOccurrenceCountry(), output.occurrenceCountry());
            assertEquals(dummyOccurrence.getOccurrenceAerodrome(), output.occurrenceAerodrome());
            assertEquals(dummyOccurrence.getOccurrenceDate(), output.occurrenceDate());
            assertEquals(dummyOccurrence.getOccurrenceTime(), output.occurrenceTime());
            assertEquals(dummyOccurrence.getTotalAircraftInvolved(), output.totalAircraftInvolved());


            var aircraftDTO = output.aircraft().get(0);
            assertEquals(dummyAircraft.getAircraftRegistration(), aircraftDTO.aircraftRegistration());
            assertEquals(dummyAircraft.getVehicleType(), aircraftDTO.vehicleType());
            assertEquals(dummyAircraft.getModel(), aircraftDTO.model());
            assertEquals(dummyAircraft.getDamageLevel(), aircraftDTO.damageLevel());

            // ContributingFactor
            var factorDTO = output.contributingFactors().get(0);
            assertEquals(dummyContributingFactor.getNameFactor(), factorDTO.nameFactor());
            assertEquals(dummyContributingFactor.getAspectFactor(), factorDTO.aspectFactor());
            assertEquals(dummyContributingFactor.getAreaFactor(), factorDTO.areaFactor());

            // OccurrenceType
            OccurrenceTypeDTO typeDTO = output.occurrenceTypes().get(0);
            assertEquals(dummyOccurrenceType.getOccurrenceType(), typeDTO.occurrenceType());
            assertEquals(dummyOccurrenceType.getOccurrenceTypeCategory(), typeDTO.occurrenceTypeCategory());

            verify(repository, times(1)).findById(eq(id));

        }
    }

    @Nested
    class getAllOccurrences {

        @Test
        @DisplayName("Should return an occurrence ranking page with DESC ordering")
        void shouldReturnAnOccurrenceRankingPageWithDESOrdering() {

            //ARRANGE
            var pageNumber = 0;
            var pageSize = 10;
            var sortBy = "occurrenceDate";
            var sortDirection = "DESC";
            var filter = new OccurrenceQueryFilter();
            var totalElements = 1;
            var totalPages = 1;

            doReturn(mockPage).when(repository).findAll(any(Specification.class), any(Pageable.class));



            //ACT
            var output = service.getAllOccurrences(pageNumber, pageSize, sortBy, sortDirection, filter);

            //ASSERT
            assertNotNull(output);
            assertEquals(totalElements, output.getTotalElements());
            assertEquals(totalPages, output.getTotalPages());

            verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        }

        @Test
        @DisplayName("Should apply filter")
        void shouldApplyFilter() {

            var pageNumber = 0;
            var pageSize = 10;
            var sortBy = "occurrenceDate";
            var sortDirection = "DESC";
            var filter = new OccurrenceQueryFilter();
            filter.setClassification(OccurrenceClassification.ACIDENTE);
            doReturn(mockPage).when(repository).findAll(any(Specification.class), any(Pageable.class));

            //ACT
            var output = service.getAllOccurrences(pageNumber, pageSize, sortBy, sortDirection, filter);

            //ASSERT
            verify(repository).findAll(
                    ArgumentMatchers.<Specification<Occurrence>>argThat(Objects::nonNull),
                    any(Pageable.class)
            );

            verify(repository, times(1)).findAll(specCaptor.capture(), any(Pageable.class));

            Specification<Occurrence> capturedSpec = specCaptor.getValue();
            assertNotNull(capturedSpec);
        }
    }
}