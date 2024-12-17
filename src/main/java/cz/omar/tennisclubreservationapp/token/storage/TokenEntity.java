package cz.omar.tennisclubreservationapp.token.storage;

import cz.omar.tennisclubreservationapp.common.storage.BaseEntity;
import cz.omar.tennisclubreservationapp.user.storage.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TokenEntity extends BaseEntity {
    @Column(name = "token", unique = true)
    private String token;

    @ManyToOne
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TokenType type;
}
