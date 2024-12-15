package cz.omar.tennisclubreservationapp.repository;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.surface.business.Surface;
import cz.omar.tennisclubreservationapp.surface.mappers.SurfaceToDatabaseMapper;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceDao;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceEntity;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceRepository;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.assertj.core.api.Assertions.assertThat;


@TestPropertySource(properties = "seed-data=false")
@SpringBootTest
class SurfaceRepositoryTests {

    SurfaceRepository surfaceRepository;
    
    @Autowired
    SurfaceToDatabaseMapper surfaceToDatabaseMapper;

    @MockBean
    SurfaceDao surfaceDao;

    SurfaceEntity surfaceEntity;
    Surface surface;

    @BeforeEach
    void setUp() {
        surfaceRepository = new SurfaceRepositoryImpl(surfaceDao, surfaceToDatabaseMapper);

        surfaceEntity = new SurfaceEntity();
        surfaceEntity.setId(1L);
        surfaceEntity.setName("test");
        surfaceEntity.setRentPerMinute(55f);

        surface = new Surface();
        surface.setId(1L);
        surface.setName("test");
        surface.setRentPerMinute(55f);
    }

    @Test
    void createSurfaceTest() {
        SurfaceEntity se = new SurfaceEntity();
        se.setName("test");
        se.setRentPerMinute(55f);

        doReturn(surfaceEntity).when(surfaceDao).create(se);

        Surface createdS = surfaceRepository.create(se);
        assertThat(createdS.getName()).isEqualTo(surface.getName());
        assertThat(createdS.getRentPerMinute()).isEqualTo(surface.getRentPerMinute());

    }

    @Test
    void getSurfaceTest() {
        doReturn(surfaceEntity).when(surfaceDao).get(1L);

        Surface retrievedS = surfaceRepository.get(1L);
        assertThat(retrievedS).isEqualTo(surfaceToDatabaseMapper.entityToSurface(surfaceEntity));
    }

    @Test
    void getNonexistentSurfaceTest() {
        doReturn(null).when(surfaceDao).get(1L);

        assertThrows(RepositoryException.class, () -> {
            surfaceRepository.get(1L);
        });
    }

    @Test
    void getAllSurfacesTest() {
        SurfaceEntity surfaceEntity2 = new SurfaceEntity();
        surfaceEntity2.setId(2L);
        surfaceEntity2.setName("test2");
        surfaceEntity2.setRentPerMinute(56f);

        doReturn(Arrays.asList(surfaceEntity, surfaceEntity2)).when(surfaceDao).getAll();

        List<Surface> retrievedS = surfaceRepository.getAll();
        assertThat(retrievedS).hasSize(2);
        assertThat(retrievedS).containsExactlyInAnyOrder(surfaceToDatabaseMapper.entityToSurface(surfaceEntity),
                surfaceToDatabaseMapper.entityToSurface(surfaceEntity2));

    }
}

