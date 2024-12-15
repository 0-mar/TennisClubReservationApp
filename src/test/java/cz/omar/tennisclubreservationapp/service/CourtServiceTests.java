package cz.omar.tennisclubreservationapp.service;

import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.court.dto.CourtCreateDto;
import cz.omar.tennisclubreservationapp.court.dto.CourtDto;
import cz.omar.tennisclubreservationapp.court.dto.CourtUpdateDto;
import cz.omar.tennisclubreservationapp.court.mappers.CourtDtoToDatabaseMapper;
import cz.omar.tennisclubreservationapp.court.mappers.CourtToDtoMapper;
import cz.omar.tennisclubreservationapp.court.service.CourtService;
import cz.omar.tennisclubreservationapp.court.storage.CourtRepository;
import cz.omar.tennisclubreservationapp.surface.business.Surface;
import cz.omar.tennisclubreservationapp.surface.mappers.SurfaceToDatabaseMapper;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceEntity;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@TestPropertySource(properties = "seed-data=false")
@SpringBootTest
public class CourtServiceTests {
    CourtService courtService;

    @MockBean
    CourtRepository courtRepository;
    @MockBean
    SurfaceRepository surfaceRepository;
    @Autowired
    SurfaceToDatabaseMapper surfaceToDatabaseMapper;
    @Autowired
    CourtToDtoMapper courtToDtoMapper;
    @Autowired
    CourtDtoToDatabaseMapper courtDtoToDatabaseMapper;

    Court court;
    Surface surface;

    @BeforeEach
    void setUp() {
        courtService = new CourtService(courtRepository, surfaceRepository, surfaceToDatabaseMapper,
                courtToDtoMapper, courtDtoToDatabaseMapper);

        surface = new Surface();
        surface.setId(1L);
        surface.setName("test");
        surface.setRentPerMinute(55f);

        court = new Court();
        court.setId(1L);
        court.setName("Court");
        court.setSurface(surface);
    }

    @Test
    void createCourtTest() {
        CourtCreateDto courtCreateDto = new CourtCreateDto();
        courtCreateDto.setName("Court");
        courtCreateDto.setSurfaceId(1L);

        SurfaceEntity surfaceEntity = new SurfaceEntity();
        surfaceEntity.setId(1L);
        surfaceEntity.setName("test");
        surfaceEntity.setRentPerMinute(55f);

        doReturn(court).when(courtRepository).create(courtDtoToDatabaseMapper.createDtoToEntity(courtCreateDto, surfaceEntity));
        doReturn(surface).when(surfaceRepository).get(courtCreateDto.getSurfaceId());

        CourtDto created = courtService.create(courtCreateDto);
        assertThat(created).isEqualTo(new CourtDto(1L, "Court", 1L));
    }

    @Test
    void getCourtTest() {
        doReturn(court).when(courtRepository).get(court.getId());

        CourtDto retrieved = courtService.get(court.getId());
        assertThat(retrieved).isEqualTo(courtToDtoMapper.courtToCourtDto(court));
    }

    @Test
    void getAllCourtsTest() {
        Court court2 = new Court();
        court2.setId(2L);
        court2.setName("Court");
        court2.setSurface(surface);

        doReturn(Arrays.asList(court, court2)).when(courtRepository).getAll();

        List<CourtDto> retrieved = courtService.getAll();
        assertThat(retrieved).hasSize(2);
        assertThat(retrieved).containsExactlyInAnyOrder(courtToDtoMapper.courtToCourtDto(court),
                courtToDtoMapper.courtToCourtDto(court2));
    }

    @Test
    void updateCourtTest() {
        CourtUpdateDto courtUpdateDto = new CourtUpdateDto(court.getId(), "Courttt", 2L);

        Surface surface2 = new Surface();
        surface2.setId(2L);
        surface2.setName("testtt");
        surface2.setRentPerMinute(545f);

        Court updatedCourt = new Court();
        updatedCourt.setId(court.getId());
        updatedCourt.setName(courtUpdateDto.getName());
        updatedCourt.setSurface(surface2);

        doReturn(updatedCourt).when(courtRepository).update(courtToDtoMapper.updateDtoToCourt(courtUpdateDto, surface));
        doReturn(surface).when(surfaceRepository).get(courtUpdateDto.getSurfaceId());

        CourtDto updated = courtService.update(courtUpdateDto);
        assertThat(updated).isEqualTo(courtToDtoMapper.courtToCourtDto(updatedCourt));
    }

    @Test
    void deleteCourtTest() {
        doReturn(court).when(courtRepository).delete(court.getId());

        Court deleted = courtRepository.delete(court.getId());
        assertThat(deleted).isEqualTo(court);
    }
}
