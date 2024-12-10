package cz.omar.tennisclubreservationapp.court.storage;

import cz.omar.tennisclubreservationapp.common.storage.AbstractDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourtDaoImpl extends AbstractDao<CourtEntity> implements CourtDao {

    public CourtDaoImpl() {
        super(CourtEntity.class);
    }

    @Override
    public CourtEntity create(CourtEntity courtEntity) {
        return save(courtEntity);
    }

    @Override
    public CourtEntity get(Long id) {
        return findById(id);
    }

    @Override
    public List<CourtEntity> getAll() {
        return findAll();
    }

    @Override
    public CourtEntity delete(Long id) {
        return remove(id);
    }

    @Override
    public CourtEntity update(CourtEntity courtEntity) {
        return merge(courtEntity);
    }
}
