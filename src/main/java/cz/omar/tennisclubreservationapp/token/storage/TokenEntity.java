package cz.omar.tennisclubreservationapp.token.storage;

import cz.omar.tennisclubreservationapp.common.storage.BaseEntity;
import cz.omar.tennisclubreservationapp.user.storage.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TokenEntity extends BaseEntity {
    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "revoked")
    private boolean revoked;

    @Column(name = "expired")
    private boolean expired;

    @ManyToOne
    private UserEntity userEntity;
}
