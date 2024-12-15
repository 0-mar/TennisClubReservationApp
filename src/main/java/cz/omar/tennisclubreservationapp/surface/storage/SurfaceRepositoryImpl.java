package cz.omar.tennisclubreservationapp.surface.storage;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.surface.business.Surface;
import cz.omar.tennisclubreservationapp.surface.mappers.SurfaceToDatabaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SurfaceRepositoryImpl implements SurfaceRepository {
    private final SurfaceDao surfaceDao;
    private final SurfaceToDatabaseMapper surfaceToDatabaseMapper;

    public SurfaceRepositoryImpl(SurfaceDao surfaceDao, SurfaceToDatabaseMapper surfaceToDatabaseMapper) {
        this.surfaceDao = surfaceDao;
        this.surfaceToDatabaseMapper = surfaceToDatabaseMapper;
    }

    @Override
    public Surface create(SurfaceEntity surfaceEntity) {
        return surfaceToDatabaseMapper.entityToSurface(surfaceDao.create(surfaceEntity));
    }

    @Override
    public Surface get(Long id) {
        SurfaceEntity surfaceEntity = surfaceDao.get(id);
        if (surfaceEntity == null) {
            throw new RepositoryException("Surface entity " + id + " not found");
        }
        return surfaceToDatabaseMapper.entityToSurface(surfaceDao.get(id));
    }

    @Override
    public List<Surface> getAll() {
        return surfaceDao.getAll().stream()
                .map(surfaceToDatabaseMapper::entityToSurface)
                .collect(Collectors.toList());
    }
}
