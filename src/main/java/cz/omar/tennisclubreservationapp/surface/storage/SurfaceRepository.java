package cz.omar.tennisclubreservationapp.surface.storage;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.surface.business.Surface;
import cz.omar.tennisclubreservationapp.surface.mappers.SurfaceToDatabaseMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SurfaceRepository {
    private final SurfaceDao surfaceDao;
    private final SurfaceToDatabaseMapper surfaceToDatabaseMapper;

    public SurfaceRepository(SurfaceDao surfaceDao, SurfaceToDatabaseMapper surfaceToDatabaseMapper) {
        this.surfaceDao = surfaceDao;
        this.surfaceToDatabaseMapper = surfaceToDatabaseMapper;
    }

    public Surface create(SurfaceEntity surfaceEntity) {
        return surfaceToDatabaseMapper.entityToSurface(surfaceDao.create(surfaceEntity));
    }

    public Surface get(Long id) {
        SurfaceEntity surfaceEntity = surfaceDao.get(id);
        if (surfaceEntity == null) {
            throw new RepositoryException("Surface entity " + id + " not found");
        }
        return surfaceToDatabaseMapper.entityToSurface(surfaceDao.get(id));
    }
}
