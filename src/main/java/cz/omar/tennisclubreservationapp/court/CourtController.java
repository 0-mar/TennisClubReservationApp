package cz.omar.tennisclubreservationapp.court;
import cz.omar.tennisclubreservationapp.court.dto.CourtCreateDto;
import cz.omar.tennisclubreservationapp.court.dto.CourtDto;
import cz.omar.tennisclubreservationapp.court.dto.CourtUpdateDto;
import cz.omar.tennisclubreservationapp.court.service.CourtService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courts")
public class CourtController {
    private final CourtService courtService;

    public CourtController(CourtService courtService) {
        this.courtService = courtService;
    }

    @PostMapping("/")
    public CourtDto createCourt(@Valid @RequestBody CourtCreateDto courtCreateDto) {
        return courtService.create(courtCreateDto);
    }

    @GetMapping("/{courtId}")
    public CourtDto getCourt(@PathVariable Long courtId) {
        return courtService.get(courtId);
    }

    @GetMapping("/")
    public List<CourtDto> getAllCourts() {
        return courtService.getAll();
    }

    @PutMapping("/{courtId}")
    public CourtDto updateCourt(@PathVariable Long courtId, @Valid @RequestBody CourtUpdateDto courtUpdateDto) {
        courtUpdateDto.setId(courtId);
        return courtService.update(courtUpdateDto);
    }

    @DeleteMapping("/{courtId}")
    public CourtDto deleteCourt(@PathVariable Long courtId) {
        return courtService.delete(courtId);
    }
}
