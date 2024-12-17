package cz.omar.tennisclubreservationapp.controller;

import cz.omar.tennisclubreservationapp.common.config.AppConfig;
import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.customer.business.Customer;
import cz.omar.tennisclubreservationapp.reservation.business.Reservation;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationCreateDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationCreatedResultDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationUpdateDto;
import cz.omar.tennisclubreservationapp.reservation.service.ReservationService;
import cz.omar.tennisclubreservationapp.surface.business.Surface;
import cz.omar.tennisclubreservationapp.user.storage.UserDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(properties = "seed-data=false")
@ContextConfiguration(classes = {AppConfig.class})
@WebMvcTest
public class ReservationControllerTests {
    /*@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    Surface surface;
    Customer customer;
    Court court;
    Reservation reservation;

    @BeforeEach
    void setUp() {
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
    void createReservationTest() throws Exception {
        ReservationCreateDto reservationCreateDto = new ReservationCreateDto();
        reservationCreateDto.setStartTime(LocalDateTime.of(2023, 10, 20, 10, 0));
        reservationCreateDto.setEndTime(LocalDateTime.of(2023, 10, 20, 11, 0));
        reservationCreateDto.setDoubles(false);
        reservationCreateDto.setCourtId(1L);
        reservationCreateDto.setFirstName(customer.getFirstName());
        reservationCreateDto.setLastName(customer.getLastName());
        reservationCreateDto.setPhoneNumber(customer.getPhoneNumber());

        ReservationCreatedResultDto resultDto = new ReservationCreatedResultDto(customer.getPhoneNumber(),
                reservation.getStartTime(), reservation.getEndTime(), 600);

        Mockito.when(reservationService.create(any(ReservationCreateDto.class))).thenReturn(resultDto);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "startTime": "2023-10-20T10:00:00",
                                    "endTime": "2023-10-20T11:00:00",
                                    "doubles": false,
                                    "courtId": 1,
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "phoneNumber": "123456789"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value(customer.getPhoneNumber()));
    }

    @Test
    void getReservationTest() throws Exception {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);
        reservationDto.setStartTime(LocalDateTime.of(2023, 10, 20, 10, 0));
        reservationDto.setEndTime(LocalDateTime.of(2023, 10, 20, 11, 0));
        reservationDto.setCourtId(1L);
        reservationDto.setCustomerId(2L);

        Mockito.when(reservationService.get(1L)).thenReturn(reservationDto);

        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.courtId").value(1))
                .andExpect(jsonPath("$.customerId").value(2));
    }

    @Test
    void getAllReservationsTest() throws Exception {
        ReservationDto reservation1 = new ReservationDto();
        reservation1.setId(1L);
        reservation1.setCourtId(1L);

        ReservationDto reservation2 = new ReservationDto();
        reservation2.setId(2L);
        reservation2.setCourtId(2L);

        List<ReservationDto> reservations = Arrays.asList(reservation1, reservation2);

        Mockito.when(reservationService.getAll()).thenReturn(reservations);

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void getReservationsByCourtTest() throws Exception {
        ReservationDto reservation = new ReservationDto();
        reservation.setId(1L);
        reservation.setCourtId(1L);

        Mockito.when(reservationService.getReservationsByCourt(1L)).thenReturn(List.of(reservation));

        mockMvc.perform(get("/api/reservations?courtId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].courtId").value(1));
    }

    @Test
    void getReservationsByPhoneNumberTest() throws Exception {
        ReservationDto reservation = new ReservationDto();
        reservation.setId(1L);
        reservation.setCourtId(1L);
        reservation.setCustomerId(2L);

        Mockito.when(reservationService.getReservationsByPhoneNumber("123456789", true))
                .thenReturn(List.of(reservation));

        mockMvc.perform(get("/api/reservations?phoneNumber=123456789&onlyFuture=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].customerId").value(2))
                .andExpect(jsonPath("$[0].courtId").value(1));
    }

    @Test
    void updateReservationTest() throws Exception {
        ReservationUpdateDto reservationUpdateDto = new ReservationUpdateDto();
        reservationUpdateDto.setId(1L);
        reservationUpdateDto.setStartTime(LocalDateTime.of(2023, 10, 20, 10, 0));
        reservationUpdateDto.setEndTime(LocalDateTime.of(2023, 10, 20, 11, 0));
        reservationUpdateDto.setDoubles(false);
        reservationUpdateDto.setCourtId(1L);
        reservationUpdateDto.setCustomerId(2L);

        ReservationDto updatedReservation = new ReservationDto();
        updatedReservation.setId(1L);
        updatedReservation.setStartTime(reservationUpdateDto.getStartTime());
        updatedReservation.setEndTime(reservationUpdateDto.getEndTime());
        updatedReservation.setDoubles(false);
        updatedReservation.setCourtId(1L);
        updatedReservation.setCustomerId(2L);

        Mockito.when(reservationService.update(any(ReservationUpdateDto.class))).thenReturn(updatedReservation);

        mockMvc.perform(put("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "startTime": "2023-10-20T10:00:00",
                                    "endTime": "2023-10-20T11:00:00",
                                    "doubles": false,
                                    "courtId": 1,
                                    "customerId": 2
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.doubles").value(false))
                .andExpect(jsonPath("$.courtId").value(1))
                .andExpect(jsonPath("$.customerId").value(2));
    }

    @Test
    void deleteReservationTest() throws Exception {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(1L);

        Mockito.when(reservationService.delete(1L)).thenReturn(reservationDto);

        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }*/
}
