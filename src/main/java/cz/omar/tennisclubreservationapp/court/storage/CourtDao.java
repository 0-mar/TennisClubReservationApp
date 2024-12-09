package cz.omar.tennisclubreservationapp.court.storage;

import java.util.List;

public interface CourtDao {
    CourtEntity create(CourtEntity courtEntity);
    CourtEntity get(Long id);
    List<CourtEntity> getAll();
    CourtEntity delete(Long id);
    CourtEntity update(CourtEntity courtEntity);
}
