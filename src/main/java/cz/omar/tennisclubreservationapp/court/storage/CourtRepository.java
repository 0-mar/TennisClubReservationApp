package cz.omar.tennisclubreservationapp.court.storage;

import cz.omar.tennisclubreservationapp.court.business.Court;

import java.util.List;

public interface CourtRepository {
    Court create(CourtEntity court);
    Court get(Long id);
    List<Court> getAll();
    Court delete(Long id);
    Court update(Court court);
}
