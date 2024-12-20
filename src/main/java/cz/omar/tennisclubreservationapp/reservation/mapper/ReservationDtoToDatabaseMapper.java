package cz.omar.tennisclubreservationapp.reservation.mapper;

import cz.omar.tennisclubreservationapp.court.storage.CourtEntity;
import cz.omar.tennisclubreservationapp.customer.storage.CustomerEntity;
import cz.omar.tennisclubreservationapp.reservation.dto.ReservationCreateDto;
import cz.omar.tennisclubreservationapp.reservation.storage.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationDtoToDatabaseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", constant = "false")
    @Mapping(source = "reservationCreateDto.startTime", target = "startTime")
    @Mapping(source = "reservationCreateDto.doubles", target = "doubles")
    @Mapping(source = "reservationCreateDto.endTime", target = "endTime")
    @Mapping(source = "customerEntity", target = "customerEntity")
    @Mapping(source = "courtEntity", target = "courtEntity")
    ReservationEntity createDtoToEntity(ReservationCreateDto reservationCreateDto, CustomerEntity customerEntity, CourtEntity courtEntity);

}
