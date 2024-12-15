package cz.omar.tennisclubreservationapp.repository;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.court.mappers.CourtToDatabaseMapper;
import cz.omar.tennisclubreservationapp.court.storage.CourtDao;
import cz.omar.tennisclubreservationapp.court.storage.CourtEntity;
import cz.omar.tennisclubreservationapp.court.storage.CourtRepository;
import cz.omar.tennisclubreservationapp.court.storage.CourtRepositoryImpl;
import cz.omar.tennisclubreservationapp.surface.business.Surface;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@TestPropertySource(properties = "seed-data=false")
@SpringBootTest
public class CourtRepositoryTests {
    CourtRepository courtRepository;

    @Autowired
    CourtToDatabaseMapper courtToDatabaseMapper;

    @MockBean
    CourtDao courtDao;

    CourtEntity courtEntity;
    Court court;
    Surface surface;
    SurfaceEntity surfaceEntity;

    @BeforeEach
    void setUp() {
        courtRepository = new CourtRepositoryImpl(courtDao, courtToDatabaseMapper);

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
    }

    @Test
    void createCourtTest() {
        CourtEntity ce = new CourtEntity();
        ce.setName("Court");
        ce.setSurfaceEntity(surfaceEntity);

        doReturn(courtEntity).when(courtDao).create(ce);

        Court created = courtRepository.create(ce);
        assertThat(created).isEqualTo(courtToDatabaseMapper.entityToCourt(courtEntity));
    }

    @Test
    void getCourtTest() {
        doReturn(courtEntity).when(courtDao).get(1L);

        Court retrieved = courtRepository.get(1L);
        assertThat(retrieved).isEqualTo(courtToDatabaseMapper.entityToCourt(courtEntity));
    }

    @Test
    void getNonexistentCourtTest() {
        doReturn(null).when(courtDao).get(1L);

        assertThrows(RepositoryException.class, () -> {
            courtRepository.get(1L);
        });
    }

    @Test
    void getAllCourtsTest() {
        SurfaceEntity surfaceEntity2 = new SurfaceEntity();
        surfaceEntity2.setId(2L);
        surfaceEntity2.setName("test2");
        surfaceEntity2.setRentPerMinute(66f);

        var courtEntity2 = new CourtEntity();
        courtEntity2.setId(2L);
        courtEntity2.setName("Court2");
        courtEntity2.setSurfaceEntity(surfaceEntity2);

        doReturn(Arrays.asList(courtEntity, courtEntity2)).when(courtDao).getAll();

        List<Court> retrievedList = courtRepository.getAll();
        assertThat(retrievedList).hasSize(2);
        assertThat(retrievedList).containsExactlyInAnyOrder(courtToDatabaseMapper.entityToCourt(courtEntity),
                courtToDatabaseMapper.entityToCourt(courtEntity2));

    }

    @Test
    void updateTest() {
        Surface surface2 = new Surface();
        surface2.setId(2L);
        surface2.setName("test");
        surface2.setRentPerMinute(55f);

        Court updateData = new Court();
        updateData.setId(court.getId());
        updateData.setName("Courttt");
        updateData.setSurface(surface2);

        SurfaceEntity surfaceEntity2 = new SurfaceEntity();
        surfaceEntity2.setId(2L);
        surfaceEntity2.setName("test");
        surfaceEntity2.setRentPerMinute(55f);

        CourtEntity updateEntityData = new CourtEntity();
        updateEntityData.setId(court.getId());
        updateEntityData.setName("Courttt");
        updateEntityData.setSurfaceEntity(surfaceEntity2);

        doReturn(updateEntityData).when(courtDao).update(updateEntityData);

        Court updated = courtRepository.update(updateData);
        assertThat(updated).isEqualTo(courtToDatabaseMapper.entityToCourt(updateEntityData));

        // nonexisting id
        updateEntityData.setId(-5L);
        updateData.setId(-5L);
        doReturn(null).when(courtDao).update(updateEntityData);
        assertThrows(RepositoryException.class, () -> {
            courtRepository.update(updateData);
        });
    }

    @Test
    void deleteTest() {
        courtEntity.setDeleted(true);
        doReturn(courtEntity).when(courtDao).delete(1L);

        var deleted = courtRepository.delete(1L);

        assertThat(deleted).isEqualTo(courtToDatabaseMapper.entityToCourt(courtEntity));

        doReturn(null).when(courtDao).delete(1L);
        assertThrows(RepositoryException.class, () -> {
            courtRepository.delete(1L);
        });
    }
}
