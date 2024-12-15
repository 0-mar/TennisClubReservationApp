package cz.omar.tennisclubreservationapp.dao;

import cz.omar.tennisclubreservationapp.common.config.AppConfig;
import cz.omar.tennisclubreservationapp.court.storage.CourtEntity;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerEntity;
import cz.omar.tennisclubreservationapp.reservation.storage.ReservationDao;
import cz.omar.tennisclubreservationapp.reservation.storage.ReservationEntity;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@TestPropertySource(properties = "seed-data=false")
@ContextConfiguration(classes = {AppConfig.class})
public class ReservationDaoTests {
    @Autowired
    ReservationDao reservationDao;

    @Autowired
    TestEntityManager entityManager;

    List<CourtEntity> courtEntities;
    List<CustomerEntity> customerEntities;
    List<ReservationEntity> reservationEntities;

    @BeforeEach
    void setUp() {
        courtEntities = initCourtData();
        customerEntities = initCustomerData();

        ReservationEntity r1 = new ReservationEntity();
        r1.setDoubles(false);
        r1.setStartTime(LocalDateTime.of(2024, Month.DECEMBER, 10, 9, 30));
        r1.setEndTime(LocalDateTime.of(2024, Month.DECEMBER, 10, 11, 0));
        r1.setCourtEntity(courtEntities.get(0));
        r1.setCustomerEntity(customerEntities.get(0));
        entityManager.persist(r1);

        ReservationEntity r2 = new ReservationEntity();
        r2.setDoubles(true);
        r2.setStartTime(LocalDateTime.of(2024, Month.DECEMBER, 11, 14, 0));
        r2.setEndTime(LocalDateTime.of(2024, Month.DECEMBER, 11, 18, 0));
        r2.setCourtEntity(courtEntities.get(0));
        r2.setCustomerEntity(customerEntities.get(1));
        entityManager.persist(r2);

        ReservationEntity r3 = new ReservationEntity();
        r3.setDoubles(false);
        r3.setStartTime(LocalDateTime.of(2024, Month.DECEMBER, 11, 19, 0));
        r3.setEndTime(LocalDateTime.of(2024, Month.DECEMBER, 11, 21, 0));
        r3.setCourtEntity(courtEntities.get(0));
        r3.setCustomerEntity(customerEntities.get(0));
        r3.setDeleted(true);
        entityManager.persist(r3);

        ReservationEntity r4 = new ReservationEntity();
        r4.setDoubles(false);
        r4.setStartTime(LocalDateTime.of(2024, Month.DECEMBER, 10, 14, 0));
        r4.setEndTime(LocalDateTime.of(2024, Month.DECEMBER, 10, 15, 30));
        r4.setCourtEntity(courtEntities.get(1));
        r4.setCustomerEntity(customerEntities.get(0));
        entityManager.persist(r4);

        reservationEntities = List.of(r1, r2 ,r3, r4);
    }

    @Test
    void getReservationsByCourt_Test() {
        List<ReservationEntity> result = reservationDao.getReservationsByCourt(courtEntities.get(0).getId());
        assertThat(result).containsExactlyInAnyOrder(reservationEntities.get(0), reservationEntities.get(1));
    }

    @Test
    void getReservationsByPhoneNumber_Test() {
        List<ReservationEntity> result = reservationDao.getReservationsByPhoneNumber(
                customerEntities.get(0).getPhoneNumber(), false);
        assertThat(result).containsExactlyInAnyOrder(reservationEntities.get(0), reservationEntities.get(3));
    }

    @Test
    void getReservationsByPhoneNumber_InFuture_Test() {
        LocalDateTime tomorrow = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusDays(1);

        ReservationEntity future1 = new ReservationEntity();
        future1.setDoubles(false);
        future1.setStartTime(tomorrow.plusHours(9));
        future1.setEndTime(tomorrow.plusHours(11));
        future1.setCourtEntity(courtEntities.get(1));
        future1.setCustomerEntity(customerEntities.get(0));
        var correct = entityManager.persist(future1);

        ReservationEntity future2 = new ReservationEntity();
        future2.setDoubles(false);
        future2.setStartTime(tomorrow.plusHours(13));
        future2.setEndTime(tomorrow.plusHours(15));
        future2.setCourtEntity(courtEntities.get(1));
        future2.setCustomerEntity(customerEntities.get(1));
        entityManager.persist(future2);

        List<ReservationEntity> result = reservationDao.getReservationsByPhoneNumber(
                customerEntities.get(0).getPhoneNumber(), true);
        assertThat(result).containsExactlyInAnyOrder(correct);
    }

    @Test
    void intervalOverlaps_Test() {
        var start = LocalDateTime.of(2024, Month.DECEMBER, 11, 13, 0);
        var end = LocalDateTime.of(2024, Month.DECEMBER, 11, 14, 30);
        assertThat(reservationDao.intervalOverlaps(start, end)).isTrue();

        start = LocalDateTime.of(2024, Month.DECEMBER, 11, 18, 30);
        end = LocalDateTime.of(2024, Month.DECEMBER, 11, 20, 30);
        assertThat(reservationDao.intervalOverlaps(start, end)).isFalse();
    }

    private List<SurfaceEntity> initializeSurfaceEntities() {
        SurfaceEntity grass = new SurfaceEntity();
        grass.setName("Grass");
        grass.setRentPerMinute(80f);

        SurfaceEntity clay = new SurfaceEntity();
        clay.setName("Clay");
        clay.setRentPerMinute(60f);

        SurfaceEntity saved1 = entityManager.persist(grass);
        SurfaceEntity saved2 = entityManager.persist(clay);

        return List.of(saved1, saved2);
    }

    private List<CourtEntity> initCourtData() {
        List<SurfaceEntity> surfaceEntities = initializeSurfaceEntities();

        CourtEntity court1 = new CourtEntity();
        court1.setName("Court 1");
        court1.setSurfaceEntity(surfaceEntities.get(0));

        CourtEntity court2 = new CourtEntity();
        court2.setName("Court 2");
        court2.setSurfaceEntity(surfaceEntities.get(1));

        CourtEntity court3 = new CourtEntity();
        court3.setName("Court 3");
        court3.setSurfaceEntity(surfaceEntities.get(0));

        CourtEntity court4 = new CourtEntity();
        court4.setName("Court 4");
        court4.setSurfaceEntity(surfaceEntities.get(1));

        var saved1 = entityManager.persist(court1);
        var saved2 = entityManager.persist(court2);
        var saved3 = entityManager.persist(court3);
        var saved4 = entityManager.persist(court4);

        return List.of(saved1, saved2, saved3, saved4);
    }

    private List<CustomerEntity> initCustomerData() {
        var saved1 = entityManager.persist(new CustomerEntity("John", "Doe", "+420789456123", false));
        var saved2 = entityManager.persist(new CustomerEntity("Pepa", "Vomacka", "+49666833945", false));

        return List.of(saved1, saved2);
    }
}
