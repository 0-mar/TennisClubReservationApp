package cz.omar.tennisclubreservationapp.reservation.service;

import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.court.mappers.CourtToDatabaseMapper;
import cz.omar.tennisclubreservationapp.court.storage.CourtRepository;
import cz.omar.tennisclubreservationapp.customer.business.Customer;
import cz.omar.tennisclubreservationapp.customer.mapper.CustomerToDatabaseMapper;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerEntity;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerRepository;
import cz.omar.tennisclubreservationapp.reservation.business.Reservation;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationCreateDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationCreatedResultDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationUpdateDto;
import cz.omar.tennisclubreservationapp.reservation.mapper.ReservationDtoToDatabaseMapper;
import cz.omar.tennisclubreservationapp.reservation.mapper.ReservationToDtoMapper;
import cz.omar.tennisclubreservationapp.reservation.storage.ReservationEntity;
import cz.omar.tennisclubreservationapp.reservation.storage.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final CourtRepository courtRepository;

    private final ReservationDtoToDatabaseMapper reservationDtoToDatabaseMapper;
    private final ReservationToDtoMapper reservationToDtoMapper;
    private final CustomerToDatabaseMapper customerToDatabaseMapper;
    private final CourtToDatabaseMapper courtToDatabaseMapper;

    public Customer getAssociatedCustomer(String phoneNumber, String firstName, String lastName) {
        Customer associatedCustomer = customerRepository.getByPhoneNumber(phoneNumber);
        if (associatedCustomer == null) {
            CustomerEntity newCustomerEntity = new CustomerEntity();
            newCustomerEntity.setPhoneNumber(phoneNumber);
            newCustomerEntity.setFirstName(firstName);
            newCustomerEntity.setLastName(lastName);

            associatedCustomer = customerRepository.create(newCustomerEntity);
        }

        return associatedCustomer;
    }

    public ReservationCreatedResultDto create(ReservationCreateDto reservationCreateDto) {
        Customer associatedCustomer = getAssociatedCustomer(reservationCreateDto.getPhoneNumber(),
                reservationCreateDto.getFirstName(), reservationCreateDto.getLastName());
        Court associatedCourt = courtRepository.get(reservationCreateDto.getCourtId());
        ReservationEntity reservationEntity = reservationDtoToDatabaseMapper.createDtoToEntity(reservationCreateDto,
                customerToDatabaseMapper.customerToEntity(associatedCustomer),
                courtToDatabaseMapper.courtToEntity(associatedCourt));

        Reservation reservation = reservationRepository.create(reservationEntity);
        Duration duration = Duration.between(reservation.getStartTime(), reservation.getEndTime());

        float price = duration.toMinutes() * reservation.getCourt().getSurface().getRentPerMinute();
        if (reservation.isDoubles()) {
            price *= 1.5f;
        }

        return new ReservationCreatedResultDto(associatedCustomer.getPhoneNumber(), reservation.getStartTime(), reservation.getEndTime(), price);
    }

    public ReservationDto get(Long id) {
        return reservationToDtoMapper.reservationToReservationDto(reservationRepository.get(id));
    }

    public List<ReservationDto> getAll() {
        return reservationRepository.getAll().stream()
                .map(reservationToDtoMapper::reservationToReservationDto)
                .collect(Collectors.toList());
    }

    public ReservationDto delete(Long id) {
        return reservationToDtoMapper.reservationToReservationDto(reservationRepository.delete(id));
    }

    public ReservationDto update(ReservationUpdateDto reservationUpdateDto) {
        Customer associatedCustomer = customerRepository.get(reservationUpdateDto.getId());
        Court associatedCourt = courtRepository.get(reservationUpdateDto.getCourtId());
        Reservation reservation = reservationToDtoMapper.updateDtoToReservation(reservationUpdateDto,
                associatedCustomer, associatedCourt);

        return reservationToDtoMapper.reservationToReservationDto(reservationRepository.update(reservation));
    }

    public List<ReservationDto> getReservationsByCourt(Long courtId) {
        return reservationRepository.getReservationsByCourt(courtId).stream()
                .map(reservationToDtoMapper::reservationToReservationDto)
                .collect(Collectors.toList());
    }

    public List<ReservationDto> getReservationsByPhoneNumber(String phoneNumber, boolean onlyFuture) {
        return reservationRepository.getReservationsByPhoneNumber(phoneNumber, onlyFuture).stream()
                .map(reservationToDtoMapper::reservationToReservationDto)
                .collect(Collectors.toList());
    }
}
