package cz.omar.tennisclubreservationapp.user.dto;

import cz.omar.tennisclubreservationapp.user.storage.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private Role role;
}
