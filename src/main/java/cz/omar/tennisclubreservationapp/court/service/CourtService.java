package cz.omar.tennisclubreservationapp.court.service;

import cz.omar.tennisclubreservationapp.court.dto.CourtDto;
import cz.omar.tennisclubreservationapp.court.storage.CourtDao;
import cz.omar.tennisclubreservationapp.court.storage.CourtEntity;
import cz.omar.tennisclubreservationapp.court.storage.CourtRepository;
import org.springframework.stereotype.Service;

@Service
public class CourtService {
    private final CourtRepository courtRepository;

    public CourtService(CourtRepository courtRepository) {
        this.courtRepository = courtRepository;
    }

    public void createCourt(CourtDto courtDto) {
        CourtEntity courtEntity = new CourtEntity();
        courtEntity.setName(courtDto.getName());

    }
}
