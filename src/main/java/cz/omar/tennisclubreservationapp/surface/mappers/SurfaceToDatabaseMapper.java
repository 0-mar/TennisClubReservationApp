package cz.omar.tennisclubreservationapp.surface.mappers;

import cz.omar.tennisclubreservationapp.surface.business.Surface;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SurfaceToDatabaseMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "rentPerMinute", target = "rentPerMinute")
    Surface entityToSurface(SurfaceEntity surfaceEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "rentPerMinute", target = "rentPerMinute")
    @Mapping(constant = "false", target = "deleted")
    SurfaceEntity surfaceToEntity(Surface surface);
}
