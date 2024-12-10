package cz.omar.tennisclubreservationapp.court.mappers;

import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.court.dto.CourtDto;
import cz.omar.tennisclubreservationapp.court.dto.CourtUpdateDto;
import cz.omar.tennisclubreservationapp.surface.business.Surface;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourtToDtoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surface.id", target = "surfaceId")
    CourtDto courtToCourtDto(Court court);

    @Mapping(source = "courtUpdateDto.id", target = "id")
    @Mapping(source = "courtUpdateDto.name", target = "name")
    @Mapping(source = "surface", target = "surface")
    Court updateDtoToCourt(CourtUpdateDto courtUpdateDto, Surface surface);
}
