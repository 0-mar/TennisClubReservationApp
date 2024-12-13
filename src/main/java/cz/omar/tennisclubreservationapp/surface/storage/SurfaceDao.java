package cz.omar.tennisclubreservationapp.surface.storage;

import java.util.List;

public interface SurfaceDao {
    SurfaceEntity create(SurfaceEntity surfaceEntity);
    SurfaceEntity get(Long id);
    List<SurfaceEntity> getAll();

}
