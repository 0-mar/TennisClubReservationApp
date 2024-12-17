package cz.omar.tennisclubreservationapp.service;

import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.court.mappers.CourtToDatabaseMapper;
import cz.omar.tennisclubreservationapp.court.storage.CourtRepository;
import cz.omar.tennisclubreservationapp.customer.business.Customer;
import cz.omar.tennisclubreservationapp.customer.mapper.CustomerToDatabaseMapper;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerRepository;
import cz.omar.tennisclubreservationapp.reservation.business.Reservation;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationCreateDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationCreatedResultDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationUpdateDto;
import cz.omar.tennisclubreservationapp.reservation.mapper.ReservationDtoToDatabaseMapper;
import cz.omar.tennisclubreservationapp.reservation.mapper.ReservationToDtoMapper;
import cz.omar.tennisclubreservationapp.reservation.service.ReservationService;
import cz.omar.tennisclubreservationapp.reservation.storage.ReservationEntity;
import cz.omar.tennisclubreservationapp.reservation.storage.ReservationRepository;
import cz.omar.tennisclubreservationapp.surface.business.Surface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@TestPropertySource(properties = "seed-data=false")
@SpringBootTest
public class ReservationServiceTests {

    ReservationService reservationService;

    @MockBean
    ReservationRepository reservationRepository;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    CourtRepository courtRepository;

    @Autowired
    ReservationDtoToDatabaseMapper reservationDtoToDatabaseMapper;

    @Autowired
    ReservationToDtoMapper reservationToDtoMapper;

    @Autowired
    CustomerToDatabaseMapper customerToDatabaseMapper;

    @Autowired
    CourtToDatabaseMapper courtToDatabaseMapper;

    Surface surface;
    Customer customer;
    Court court;
    Reservation reservation;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(reservationRepository, customerRepository, courtRepository,
                reservationDtoToDatabaseMapper, reservationToDtoMapper, customerToDatabaseMapper, courtToDatabaseMapper);

        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setPhoneNumber("123456789");

        surface = new Surface();
        surface.setId(1L);
        surface.setName("Grass");
        surface.setRentPerMinute(10f);

        court = new Court();
        court.setId(1L);
        court.setName("Court 1");
        court.setSurface(surface);

        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStartTime(LocalDateTime.of(2023, 10, 20, 10, 0));
        reservation.setEndTime(LocalDateTime.of(2023, 10, 20, 11, 0));
        reservation.setDoubles(false);
        reservation.setCourt(court);
        reservation.setCustomer(customer);
    }

    @Test
    void createReservationTest() {
        ReservationCreateDto reservationCreateDto = new ReservationCreateDto();
        reservationCreateDto.setStartTime(reservation.getStartTime());
        reservationCreateDto.setEndTime(reservation.getEndTime());
        reservationCreateDto.setDoubles(reservation.isDoubles());
        reservationCreateDto.setCourtId(1L);
        reservationCreateDto.setFirstName("John");
        reservationCreateDto.setLastName("Doe");
        reservationCreateDto.setPhoneNumber("123456789");

        ReservationCreatedResultDto createdResultDto = new ReservationCreatedResultDto(reservationCreateDto.getPhoneNumber(),
                reservation.getStartTime(), reservation.getEndTime(), 600);

        doReturn(customer).when(customerRepository).getByPhoneNumber(customer.getPhoneNumber());
        doReturn(court).when(courtRepository).get(court.getId());
        doReturn(reservation).when(reservationRepository).create(Mockito.any(ReservationEntity.class));

        var result = reservationService.create(reservationCreateDto);
        assertThat(result).isEqualTo(createdResultDto);
    }

    @Test
    void getReservationTest() {
        doReturn(reservation).when(reservationRepository).get(1L);

        ReservationDto retrieved = reservationService.get(1L);
        assertThat(retrieved).isEqualTo(reservationToDtoMapper.reservationToReservationDto(reservation));
    }

    @Test
    void getAllReservationsTest() {
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setCourt(court);
        reservation1.setCustomer(customer);
        reservation1.setStartTime(LocalDateTime.of(2023, 10, 20, 10, 0));
        reservation1.setEndTime(LocalDateTime.of(2023, 10, 20, 11, 0));

        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setCourt(court);
        reservation2.setCustomer(customer);
        reservation2.setStartTime(LocalDateTime.of(2023, 10, 20, 12, 0));
        reservation2.setEndTime(LocalDateTime.of(2023, 10, 20, 14, 0));

        doReturn(Arrays.asList(reservation1, reservation2)).when(reservationRepository).getAll();

        List<ReservationDto> reservations = reservationService.getAll();
        assertThat(reservations).containsExactlyInAnyOrder(reservationToDtoMapper.reservationToReservationDto(reservation1),
                reservationToDtoMapper.reservationToReservationDto(reservation2));
    }

    @Test
    void updateReservationTest() {
        ReservationUpdateDto reservationUpdateDto = new ReservationUpdateDto();
        reservationUpdateDto.setId(1L);
        reservationUpdateDto.setStartTime(LocalDateTime.of(2023, 10, 20, 14, 0));
        reservationUpdateDto.setEndTime(LocalDateTime.of(2023, 10, 20, 16, 0));
        reservationUpdateDto.setDoubles(true);
        reservationUpdateDto.setCourtId(1L);
        reservationUpdateDto.setCustomerId(1L);

        Reservation updatedReservation = new Reservation();
        updatedReservation.setId(reservationUpdateDto.getId());
        updatedReservation.setStartTime(reservationUpdateDto.getStartTime());
        updatedReservation.setEndTime(reservationUpdateDto.getEndTime());
        updatedReservation.setDoubles(reservationUpdateDto.isDoubles());
        updatedReservation.setCourt(court);
        updatedReservation.setCustomer(customer);

        doReturn(customer).when(customerRepository).get(reservationUpdateDto.getCustomerId());
        doReturn(court).when(courtRepository).get(reservationUpdateDto.getCourtId());
        doReturn(updatedReservation).when(reservationRepository).update(Mockito.any(Reservation.class));

        ReservationDto updated = reservationService.update(reservationUpdateDto);
        assertThat(updated).isEqualTo(reservationToDtoMapper.reservationToReservationDto(updatedReservation));
    }

    @Test
    void deleteReservationTest() {
        doReturn(reservation).when(reservationRepository).delete(1L);

        ReservationDto deleted = reservationService.delete(1L);
        assertThat(deleted).isEqualTo(reservationToDtoMapper.reservationToReservationDto(reservation));
    }

    @Test
    void getReservationsByCourtTest() {
        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setCourt(court);
        reservation2.setCustomer(customer);
        reservation2.setStartTime(LocalDateTime.of(2023, 10, 19, 14, 0));
        reservation2.setEndTime(LocalDateTime.of(2023, 10, 19, 16, 0));

        var court2 = new Court();
        court2.setId(2L);
        court2.setName("Court 2");
        court2.setSurface(surface);

        Reservation reservation3 = new Reservation();
        reservation3.setId(2L);
        reservation3.setCourt(court2);
        reservation3.setCustomer(customer);
        reservation3.setStartTime(LocalDateTime.of(2023, 10, 19, 6, 0));
        reservation3.setEndTime(LocalDateTime.of(2023, 10, 19, 7, 0));

        doReturn(Arrays.asList(reservation, reservation2)).when(reservationRepository).getReservationsByCourt(1L);

        List<ReservationDto> reservations = reservationService.getReservationsByCourt(1L);
        assertThat(reservations).containsExactlyInAnyOrder(reservationToDtoMapper.reservationToReservationDto(reservation),
                reservationToDtoMapper.reservationToReservationDto(reservation2));
    }

    @Test
    void getReservationsByPhoneNumberTest() {
        var customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Pepa");
        customer2.setLastName("Doe");
        customer2.setPhoneNumber("+420489789456");

        var reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setCourt(court);
        reservation2.setCustomer(customer2);
        reservation2.setStartTime(LocalDateTime.of(2023, 10, 19, 14, 0));
        reservation2.setEndTime(LocalDateTime.of(2023, 10, 19, 16, 0));

        doReturn(Arrays.asList(reservation)).when(reservationRepository).getReservationsByPhoneNumber(customer.getPhoneNumber(), false);

        List<ReservationDto> reservations = reservationService.getReservationsByPhoneNumber(customer.getPhoneNumber(), false);
        assertThat(reservations).containsExactlyInAnyOrder(reservationToDtoMapper.reservationToReservationDto(reservation));
    }
}
