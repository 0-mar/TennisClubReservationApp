package cz.omar.tennisclubreservationapp.user.dto;

import cz.omar.tennisclubreservationapp.user.storage.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateDto {
    private Long id;
    private String password;
    private Role role;
}
