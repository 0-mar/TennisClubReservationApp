package cz.omar.tennisclubreservationapp.surface.storage;

import cz.omar.tennisclubreservationapp.common.storage.AbstractDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SurfaceDaoImpl extends AbstractDao<SurfaceEntity> implements SurfaceDao {

    public SurfaceDaoImpl() {
        super(SurfaceEntity.class);
    }

    @Override
    public SurfaceEntity create(SurfaceEntity surfaceEntity) {
        return save(surfaceEntity);
    }

    @Override
    public SurfaceEntity get(Long id) {
        return findById(id);
    }

    @Override
    public List<SurfaceEntity> getAll() {
        return findAll();
    }
}
