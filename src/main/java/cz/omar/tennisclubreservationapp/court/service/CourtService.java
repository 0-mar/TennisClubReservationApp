package cz.omar.tennisclubreservationapp.court.service;

import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.court.dto.CourtCreateDto;
import cz.omar.tennisclubreservationapp.court.dto.CourtDto;
import cz.omar.tennisclubreservationapp.court.dto.CourtUpdateDto;
import cz.omar.tennisclubreservationapp.court.mappers.CourtDtoToDatabaseMapper;
import cz.omar.tennisclubreservationapp.court.mappers.CourtToDtoMapper;
import cz.omar.tennisclubreservationapp.court.storage.CourtEntity;
import cz.omar.tennisclubreservationapp.court.storage.CourtRepository;
import cz.omar.tennisclubreservationapp.surface.business.Surface;
import cz.omar.tennisclubreservationapp.surface.mappers.SurfaceToDatabaseMapper;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class CourtService {
    private final CourtRepository courtRepository;
    private final SurfaceRepository surfaceRepository;
    private final SurfaceToDatabaseMapper surfaceToDatabaseMapper;
    private final CourtToDtoMapper courtToDtoMapper;
    private final CourtDtoToDatabaseMapper courtDtoToDatabaseMapper;

    public CourtDto create(CourtCreateDto courtDto) {
        Surface associatedSurface = surfaceRepository.get(courtDto.getSurfaceId());
        CourtEntity courtEntity = courtDtoToDatabaseMapper.createDtoToEntity(courtDto, surfaceToDatabaseMapper.surfaceToEntity(associatedSurface));
        Court court = courtRepository.create(courtEntity);

        return courtToDtoMapper.courtToCourtDto(court);
    }

    public CourtDto get(Long id) {
        return courtToDtoMapper.courtToCourtDto(courtRepository.get(id));
    }

    public List<CourtDto> getAll() {
        return courtRepository.getAll()
                .stream()
                .map(courtToDtoMapper::courtToCourtDto)
                .collect(Collectors.toList());
    }

    public CourtDto update(CourtUpdateDto courtUpdateDto) {
        Surface associatedSurface = surfaceRepository.get(courtUpdateDto.getSurfaceId());
        Court court = courtToDtoMapper.updateDtoToCourt(courtUpdateDto, associatedSurface);
        return courtToDtoMapper.courtToCourtDto(courtRepository.update(court));
    }

    public CourtDto delete(Long id) {
        return courtToDtoMapper.courtToCourtDto(courtRepository.delete(id));
    }
}
