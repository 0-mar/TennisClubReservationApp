package cz.omar.tennisclubreservationapp;

import cz.omar.tennisclubreservationapp.reservation.ReservationController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TennisClubReservationAppApplicationTests {

    @Autowired
    ReservationController reservationController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(reservationController);
    }

}
