package cz.omar.tennisclubreservationapp.court.storage;

import cz.omar.tennisclubreservationapp.common.storage.RepositoryException;
import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.court.mappers.CourtToDatabaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CourtRepositoryImpl implements CourtRepository {
    private final CourtDao courtDao;

    private final CourtToDatabaseMapper courtMapper;

    public CourtRepositoryImpl(CourtDao courtDao, CourtToDatabaseMapper courtMapper) {
        this.courtDao = courtDao;
        this.courtMapper = courtMapper;
    }


    @Override
    public Court create(CourtEntity courtEntity) {
        return courtMapper.entityToCourt(courtDao.create(courtEntity));
    }

    @Override
    public Court get(Long id) {
        CourtEntity courtEntity = courtDao.get(id);
        if (courtEntity == null) {
            throw new RepositoryException("Court " + id + " not found");
        }

        return courtMapper.entityToCourt(courtEntity);
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
        CourtEntity deletedCourt = courtDao.delete(id);
        if (deletedCourt == null) {
            throw new RepositoryException("Court " + id + " not found");
        }
        return courtMapper.entityToCourt(deletedCourt);
    }

    @Override
    public Court update(Court court) {
        CourtEntity updatedCourt = courtDao.update(courtMapper.courtToEntity(court));
        if (updatedCourt == null) {
            throw new RepositoryException("Court " + court.getId() + " not found");
        }
        return courtMapper.entityToCourt(updatedCourt);
    }
}
