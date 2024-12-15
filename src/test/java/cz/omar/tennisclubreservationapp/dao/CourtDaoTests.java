package cz.omar.tennisclubreservationapp.dao;

import cz.omar.tennisclubreservationapp.common.config.AppConfig;
import cz.omar.tennisclubreservationapp.court.storage.CourtDao;
import cz.omar.tennisclubreservationapp.court.storage.CourtEntity;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = "seed-data=false")
@ContextConfiguration(classes = {AppConfig.class})
public class CourtDaoTests {
    @Autowired
    private CourtDao courtDao;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void testCreateCourt() {
        CourtEntity court = new CourtEntity();
        court.setName("Test");
        SurfaceEntity surface = new SurfaceEntity();
        surface.setName("Surface");
        surface.setRentPerMinute(10f);
        entityManager.persist(surface);

        court.setSurfaceEntity(surface);

        CourtEntity savedCourt = courtDao.create(court);
        assertThat(entityManager.find(CourtEntity.class, savedCourt.getId()) ).isEqualTo(savedCourt);

    }

    @Test
    void testGet() {
        CourtEntity court = new CourtEntity();
        court.setName("Test");

        SurfaceEntity surface = new SurfaceEntity();
        surface.setName("Surface");
        surface.setRentPerMinute(10f);
        entityManager.persist(surface);

        CourtEntity savedCourt = entityManager.persist(court);

        CourtEntity foundCourt = courtDao.get(savedCourt.getId());

        assertThat(savedCourt).isEqualTo(foundCourt);
    }

    @Test
    void testGetAll() {
        CourtEntity court1 = new CourtEntity();
        court1.setName("Test");
        SurfaceEntity surface = new SurfaceEntity();
        surface.setName("Surface");
        surface.setRentPerMinute(10f);
        entityManager.persist(surface);
        court1.setSurfaceEntity(surface);

        CourtEntity court2 = new CourtEntity();
        court2.setName("Test");
        court2.setSurfaceEntity(surface);

        CourtEntity savedCourt1 = entityManager.persist(court1);
        CourtEntity savedCourt2 = entityManager.persist(court2);

        List<CourtEntity> courts = courtDao.getAll();

        assertThat(courts).hasSize(2);
        assertThat(courts).containsExactlyInAnyOrder(savedCourt1, savedCourt2);
    }

    @Test
    void testUpdate() {
        CourtEntity court1 = new CourtEntity();
        court1.setName("Test");
        SurfaceEntity surface = new SurfaceEntity();
        surface.setName("Surface");
        surface.setRentPerMinute(10f);
        entityManager.persist(surface);
        court1.setSurfaceEntity(surface);
        entityManager.persist(court1);

        SurfaceEntity surface2 = new SurfaceEntity();
        surface.setName("Grass");
        surface.setRentPerMinute(20f);
        entityManager.persist(surface2);

        CourtEntity updateData = new CourtEntity();
        updateData.setId(court1.getId());
        updateData.setName("Testtt");
        //updateData.setSurfaceEntity(surface2);

        CourtEntity updated = courtDao.update(updateData);
        assertThat(updated).isEqualTo(entityManager.find(CourtEntity.class, court1.getId()));

        CourtEntity updateData2 = new CourtEntity();
        updateData2.setId(-5L);
        updateData2.setName("Test");
        updateData2.setSurfaceEntity(surface2);
        CourtEntity updated2 = courtDao.update(updateData2);

        assertThat(updated2).isEqualTo(null);
    }

    @Test
    void testDelete() {
        CourtEntity court = new CourtEntity();
        court.setName("Test");
        SurfaceEntity surface = new SurfaceEntity();
        surface.setName("Surface");
        surface.setRentPerMinute(10f);
        entityManager.persist(surface);
        court.setSurfaceEntity(surface);

        CourtEntity savedCourt = entityManager.persist(court);

        CourtEntity deletedCourt = courtDao.delete(savedCourt.getId());
        CourtEntity foundCourt = entityManager.find(CourtEntity.class, savedCourt.getId());

        assertThat(deletedCourt).isEqualTo(foundCourt);
        assertThat(deletedCourt.isDeleted()).isTrue();

        CourtEntity deletedCourt2 = courtDao.delete(savedCourt.getId());
        assertThat(deletedCourt2).isEqualTo(null);
    }
}
