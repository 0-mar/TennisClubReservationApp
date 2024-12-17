package cz.omar.tennisclubreservationapp.mapper;

import cz.omar.tennisclubreservationapp.common.config.AppConfig;
import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.court.dto.CourtCreateDto;
import cz.omar.tennisclubreservationapp.court.dto.CourtDto;
import cz.omar.tennisclubreservationapp.court.dto.CourtUpdateDto;
import cz.omar.tennisclubreservationapp.court.mappers.CourtDtoToDatabaseMapper;
import cz.omar.tennisclubreservationapp.court.mappers.CourtToDatabaseMapper;
import cz.omar.tennisclubreservationapp.court.mappers.CourtToDtoMapper;
import cz.omar.tennisclubreservationapp.court.storage.CourtEntity;
import cz.omar.tennisclubreservationapp.surface.business.Surface;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "seed-data=false")
@ContextConfiguration(classes = {AppConfig.class})
@DataJpaTest
public class CourtMapperTests {
    @Autowired
    CourtToDatabaseMapper courtToDatabaseMapper;

    @Autowired
    CourtToDtoMapper courtToDtoMapper;

    @Autowired
    CourtDtoToDatabaseMapper courtDtoToDatabaseMapper;

    Court court;
    CourtEntity courtEntity;
    Surface surface;
    SurfaceEntity surfaceEntity;
    CourtDto courtDto;

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

        court = new Court();
        court.setId(1L);
        court.setName("Court");
        court.setSurface(surface);

        courtEntity = new CourtEntity();
        courtEntity.setId(1L);
        courtEntity.setName("Court");
        courtEntity.setSurfaceEntity(surfaceEntity);

        courtDto = new CourtDto();
        courtDto.setId(1L);
        courtDto.setName("Court");
        courtDto.setSurfaceId(1L);
    }

    @Test
    void courtEntityToCourtTest() {
        assertThat(courtToDatabaseMapper.courtToEntity(court)).isEqualTo(courtEntity);
    }

    @Test
    void courtToCourtEntityTest() {
        assertThat(courtToDatabaseMapper.entityToCourt(courtEntity)).isEqualTo(court);
    }

    @Test
    void courtToCourtDtoTest() {
        assertThat(courtToDtoMapper.courtToCourtDto(court)).isEqualTo(courtDto);
    }

    @Test
    void updateDtoToCourtTest() {
        CourtUpdateDto courtUpdateDto = new CourtUpdateDto();
        courtUpdateDto.setId(1L);
        courtUpdateDto.setName("Court");
        courtUpdateDto.setSurfaceId(1L);

        assertThat(courtToDtoMapper.updateDtoToCourt(courtUpdateDto, surface)).isEqualTo(court);
    }

    @Test
    void createDtoToEntityTest() {
        courtEntity.setId(null);

        CourtCreateDto courtCreateDto = new CourtCreateDto();
        courtCreateDto.setName("Court");
        courtCreateDto.setSurfaceId(1L);

        assertThat(courtDtoToDatabaseMapper.createDtoToEntity(courtCreateDto, surfaceEntity)).isEqualTo(courtEntity);
    }
}
