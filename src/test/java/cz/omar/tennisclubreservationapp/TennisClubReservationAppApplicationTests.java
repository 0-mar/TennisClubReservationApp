package cz.omar.tennisclubreservationapp;

import cz.omar.tennisclubreservationapp.reservation.ReservationController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "seed-data=false")
@SpringBootTest
class TennisClubReservationAppApplicationTests {

    @Autowired
    ReservationController reservationController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(reservationController);
    }

}
