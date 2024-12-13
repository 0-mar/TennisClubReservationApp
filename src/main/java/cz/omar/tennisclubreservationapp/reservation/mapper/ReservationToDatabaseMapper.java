package cz.omar.tennisclubreservationapp.reservation.mapper;

import cz.omar.tennisclubreservationapp.court.mappers.CourtToDatabaseMapper;
import cz.omar.tennisclubreservationapp.customer.mapper.CustomerToDatabaseMapper;
import cz.omar.tennisclubreservationapp.reservation.business.Reservation;
import cz.omar.tennisclubreservationapp.reservation.storage.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CustomerToDatabaseMapper.class, CourtToDatabaseMapper.class})
public interface ReservationToDatabaseMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "doubles", target = "doubles")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "customerEntity", target = "customer")
    @Mapping(source = "courtEntity", target = "court")
    Reservation entityToReservation(ReservationEntity reservationEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(target = "deleted", constant = "false")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "doubles", target = "doubles")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "customer", target = "customerEntity")
    @Mapping(source = "court", target = "courtEntity")
    ReservationEntity reservationToEntity(Reservation reservation);
}
