package cz.omar.tennisclubreservationapp.court.storage;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.court.mappers.CourtToDatabaseMapper;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class CourtRepositoryImpl implements CourtRepository {
    private final CourtDao courtDao;
    private final SurfaceDao surfaceDao;

    private final CourtToDatabaseMapper courtMapper;

    public CourtRepositoryImpl(CourtDao courtDao, SurfaceDao surfaceDao, CourtToDatabaseMapper courtMapper) {
        this.courtDao = courtDao;
        this.surfaceDao = surfaceDao;
        this.courtMapper = courtMapper;
    }


    @Override
    public Court create(CourtEntity courtEntity) {
        if (surfaceDao.get(courtEntity.getSurfaceEntity().getId()) == null) {
            throw new RepositoryException("Surface entity " + courtEntity.getSurfaceEntity() + " not found");
        }

        return courtMapper.entityToCourt(courtDao.create(courtEntity));
    }

    @Override
    public Court get(Long id) {
        return courtMapper.entityToCourt(courtDao.get(id));
    }

    @Override
    public List<Court> getAll() {
        return courtDao.getAll()
                .stream()
                .map(courtMapper::entityToCourt)
                .collect(Collectors.toList());
    }

    @Override
    public Court delete(Long id) {
        return courtMapper.entityToCourt(courtDao.delete(id));
    }

    @Override
    public Court update(Court court) {
        return courtMapper.entityToCourt(courtDao.update(courtMapper.courtToEntity(court)));
    }
}
