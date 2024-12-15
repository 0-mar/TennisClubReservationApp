package cz.omar.tennisclubreservationapp.court.mappers;

import cz.omar.tennisclubreservationapp.court.dto.CourtCreateDto;
import cz.omar.tennisclubreservationapp.court.storage.CourtEntity;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourtDtoToDatabaseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "courtCreateDto.name", target = "name")
    @Mapping(source = "surfaceEntity", target = "surfaceEntity")
    CourtEntity createDtoToEntity(CourtCreateDto courtCreateDto, SurfaceEntity surfaceEntity);
}
