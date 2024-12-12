package cz.omar.tennisclubreservationapp.reservation.mapper;

import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.customer.business.Customer;
import cz.omar.tennisclubreservationapp.reservation.business.Reservation;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationDto;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationToDtoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "from", target = "from")
    @Mapping(source = "doubles", target = "doubles")
    @Mapping(source = "to", target = "to")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "court.id", target = "courtId")
    ReservationDto reservationToReservationDto(Reservation reservation);

    @Mapping(source = "courtUpdateDto.id", target = "id")
    @Mapping(source = "courtUpdateDto.from", target = "from")
    @Mapping(source = "courtUpdateDto.doubles", target = "doubles")
    @Mapping(source = "courtUpdateDto.to", target = "to")
    @Mapping(source = "court", target = "court")
    @Mapping(source = "customer", target = "customer")
    Reservation updateDtoToReservation(ReservationUpdateDto courtUpdateDto, Customer customer, Court court);
}
