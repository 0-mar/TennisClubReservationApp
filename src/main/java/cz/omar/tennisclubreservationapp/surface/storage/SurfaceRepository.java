package cz.omar.tennisclubreservationapp.surface.storage;

import cz.omar.tennisclubreservationapp.surface.business.Surface;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurfaceRepository {
    Surface create(SurfaceEntity surfaceEntity);

    Surface get(Long id);

    List<Surface> getAll();
}
