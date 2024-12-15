package cz.omar.tennisclubreservationapp.mapper;

import cz.omar.tennisclubreservationapp.common.config.AppConfig;
import cz.omar.tennisclubreservationapp.surface.business.Surface;
import cz.omar.tennisclubreservationapp.surface.mappers.SurfaceToDatabaseMapper;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "seed-data=false")
@ContextConfiguration(classes = {AppConfig.class})
public class SurfaceMapperTests {

    @Autowired
    SurfaceToDatabaseMapper surfaceToDatabaseMapper;

    Surface surface;
    SurfaceEntity surfaceEntity;

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
    }

    @Test
    void surfaceEntityToSurfaceTest() {
        assertThat(surfaceToDatabaseMapper.surfaceToEntity(surface)).isEqualTo(surfaceEntity);
    }

    @Test
    void surfaceToSurfaceEntityTest() {
        assertThat(surfaceToDatabaseMapper.entityToSurface(surfaceEntity)).isEqualTo(surface);
    }

}
