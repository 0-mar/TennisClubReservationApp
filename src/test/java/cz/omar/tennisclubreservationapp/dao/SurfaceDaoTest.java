package cz.omar.tennisclubreservationapp.dao;

import cz.omar.tennisclubreservationapp.common.config.AppConfig;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceDao;
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
public class SurfaceDaoTest {
    @Autowired
    private SurfaceDao surfaceDao;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void testCreateSurface() {
        SurfaceEntity surface = new SurfaceEntity();
        surface.setName("Grass");
        surface.setRentPerMinute(1.5f);

        SurfaceEntity savedSurface = surfaceDao.create(surface);
        assertThat(entityManager.find(SurfaceEntity.class, savedSurface.getId()) ).isEqualTo(savedSurface);

    }

    @Test
    void testGet() {
        SurfaceEntity surface = new SurfaceEntity();
        surface.setName("Clay");
        surface.setRentPerMinute(2.0f);

        SurfaceEntity savedSurface = entityManager.persist(surface);

        SurfaceEntity foundSurface = surfaceDao.get(savedSurface.getId());

        assertThat(savedSurface).isEqualTo(foundSurface);
    }

    @Test
    void testGetAll() {
        SurfaceEntity surface1 = new SurfaceEntity();
        surface1.setName("Grass");
        surface1.setRentPerMinute(1.5f);

        SurfaceEntity surface2 = new SurfaceEntity();
        surface2.setName("Clay");
        surface2.setRentPerMinute(2.0f);

        SurfaceEntity savedSurface1 = entityManager.persist(surface1);
        SurfaceEntity savedSurface2 = entityManager.persist(surface2);

        List<SurfaceEntity> surfaces = surfaceDao.getAll();

        assertThat(surfaces).hasSize(2);
        assertThat(surfaces).containsExactlyInAnyOrder(savedSurface1, savedSurface2);
    }
}
