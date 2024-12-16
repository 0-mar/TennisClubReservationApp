package cz.omar.tennisclubreservationapp.auth.dto;

import cz.omar.tennisclubreservationapp.user.storage.Role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Role role;
}
