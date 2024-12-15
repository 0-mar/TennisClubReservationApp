package cz.omar.tennisclubreservationapp.mapper;

import cz.omar.tennisclubreservationapp.common.config.AppConfig;
import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.court.storage.CourtEntity;
import cz.omar.tennisclubreservationapp.customer.business.Customer;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerEntity;
import cz.omar.tennisclubreservationapp.reservation.business.Reservation;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationCreateDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationUpdateDto;
import cz.omar.tennisclubreservationapp.reservation.mapper.ReservationDtoToDatabaseMapper;
import cz.omar.tennisclubreservationapp.reservation.mapper.ReservationToDatabaseMapper;
import cz.omar.tennisclubreservationapp.reservation.mapper.ReservationToDtoMapper;
import cz.omar.tennisclubreservationapp.reservation.storage.ReservationEntity;
import cz.omar.tennisclubreservationapp.surface.business.Surface;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "seed-data=false")
@ContextConfiguration(classes = {AppConfig.class})
public class ReservationMapperTests {
    @Autowired
    ReservationToDatabaseMapper reservationToDatabaseMapper;

    @Autowired
    ReservationToDtoMapper reservationToDtoMapper;

    @Autowired
    ReservationDtoToDatabaseMapper reservationDtoToDatabaseMapper;

    Reservation reservation;
    ReservationEntity reservationEntity;
    ReservationDto reservationDto;

    Surface surface;
    SurfaceEntity surfaceEntity;

    Court court;
    CourtEntity courtEntity;

    Customer customer;
    CustomerEntity customerEntity;

    @BeforeEach
    void setUp() {
        surface = new Surface();
        surface.setId(1L);
        surface.setName("test");
        surface.setRentPerMinute(55f);

        surfaceEntity = new SurfaceEntity();
        surfaceEntity.setId(1L);
        surfaceEntity.setName("test");
        surfaceEntity.setRentPerMinute(55f);

        court = new Court();
        court.setId(1L);
        court.setName("Court");
        court.setSurface(surface);

        courtEntity = new CourtEntity();
        courtEntity.setId(1L);
        courtEntity.setName("Court");
        courtEntity.setSurfaceEntity(surfaceEntity);

        customer = new Customer();
        customer.setId(1L);
        customer.setPhoneNumber("+420645789123");
        customer.setFirstName("John");
        customer.setLastName("Smith");

        customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setPhoneNumber("+420645789123");
        customerEntity.setFirstName("John");
        customerEntity.setLastName("Smith");

        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setDoubles(true);
        reservation.setStartTime(LocalDateTime.of(2024, Month.DECEMBER, 11, 14, 0));
        reservation.setEndTime(LocalDateTime.of(2024, Month.DECEMBER, 11, 18, 0));
        reservation.setCourt(court);
        reservation.setCustomer(customer);

        reservationEntity = new ReservationEntity();
        reservationEntity.setId(1L);
        reservationEntity.setDoubles(true);
        reservationEntity.setStartTime(LocalDateTime.of(2024, Month.DECEMBER, 11, 14, 0));
        reservationEntity.setEndTime(LocalDateTime.of(2024, Month.DECEMBER, 11, 18, 0));
        reservationEntity.setCourtEntity(courtEntity);
        reservationEntity.setCustomerEntity(customerEntity);

        reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setDoubles(true);
        reservationDto.setStartTime(LocalDateTime.of(2024, Month.DECEMBER, 11, 14, 0));
        reservationDto.setEndTime(LocalDateTime.of(2024, Month.DECEMBER, 11, 18, 0));
        reservationDto.setCourtId(1L);
        reservationDto.setCustomerId(1L);
    }

    @Test
    void reservationEntityToReservationTest() {
        assertThat(reservationToDatabaseMapper.reservationToEntity(reservation)).isEqualTo(reservationEntity);
    }

    @Test
    void reservationToReservationEntityTest() {
        assertThat(reservationToDatabaseMapper.entityToReservation(reservationEntity)).isEqualTo(reservation);
    }

    @Test
    void reservationToReservationDtoTest() {
        assertThat(reservationToDtoMapper.reservationToReservationDto(reservation)).isEqualTo(reservationDto);
    }

    @Test
    void updateDtoToReservationTest() {
        var c2 = new Customer();
        c2.setId(2L);
        c2.setPhoneNumber("+420666789123");
        c2.setFirstName("Pepek");
        c2.setLastName("Vomacka");

        var s2 = new Surface();
        s2.setId(2L);
        s2.setName("testtt");
        s2.setRentPerMinute(555f);

        var co2 = new Court();
        co2.setId(2L);
        co2.setName("Courttt");
        co2.setSurface(s2);

        ReservationUpdateDto reservationUpdateDto = new ReservationUpdateDto();
        reservationUpdateDto.setId(1L);
        reservationUpdateDto.setDoubles(false);
        reservationUpdateDto.setStartTime(LocalDateTime.of(2024, Month.DECEMBER, 13, 11, 30));
        reservationUpdateDto.setEndTime(LocalDateTime.of(2024, Month.DECEMBER, 13, 12, 30));
        reservationUpdateDto.setCourtId(2L);
        reservationUpdateDto.setCustomerId(2L);

        Reservation updated = new Reservation();
        updated.setId(1L);
        updated.setDoubles(false);
        updated.setStartTime(LocalDateTime.of(2024, Month.DECEMBER, 13, 11, 30));
        updated.setEndTime(LocalDateTime.of(2024, Month.DECEMBER, 13, 12, 30));
        updated.setCourt(co2);
        updated.setCustomer(c2);

        assertThat(reservationToDtoMapper.updateDtoToReservation(reservationUpdateDto, c2, co2)).isEqualTo(updated);
    }

    @Test
    void createDtoToEntityTest() {
        reservationEntity.setId(null);

        ReservationCreateDto reservationCreateDto = new ReservationCreateDto();
        reservationCreateDto.setDoubles(true);
        reservationCreateDto.setStartTime(LocalDateTime.of(2024, Month.DECEMBER, 11, 14, 0));
        reservationCreateDto.setEndTime(LocalDateTime.of(2024, Month.DECEMBER, 11, 18, 0));
        reservationCreateDto.setCourtId(1L);
        reservationCreateDto.setPhoneNumber("+420645789123");
        reservationCreateDto.setFirstName("John");
        reservationCreateDto.setLastName("Smith");

        assertThat(reservationDtoToDatabaseMapper.createDtoToEntity(reservationCreateDto, customerEntity, courtEntity)).isEqualTo(reservationEntity);
    }
}
