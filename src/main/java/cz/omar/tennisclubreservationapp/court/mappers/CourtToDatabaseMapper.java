package cz.omar.tennisclubreservationapp.court.mappers;

import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.court.storage.CourtEntity;
import cz.omar.tennisclubreservationapp.surface.mappers.SurfaceToDatabaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SurfaceToDatabaseMapper.class})
public interface CourtToDatabaseMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surfaceEntity", target = "surface")
    Court entityToCourt(CourtEntity courtEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surface", target = "surfaceEntity")
    @Mapping(constant = "false", target = "deleted")
    CourtEntity courtToEntity(Court court);
}
