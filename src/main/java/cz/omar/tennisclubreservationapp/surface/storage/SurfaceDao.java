package cz.omar.tennisclubreservationapp.surface.storage;

public interface SurfaceDao {
    SurfaceEntity create(SurfaceEntity surfaceEntity);
    SurfaceEntity get(Long id);
}
