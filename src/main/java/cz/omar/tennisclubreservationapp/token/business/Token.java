package cz.omar.tennisclubreservationapp.token.business;

import cz.omar.tennisclubreservationapp.token.storage.TokenType;
import cz.omar.tennisclubreservationapp.user.business.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    private Long id;
    private String token;
    private User user;
    private TokenType type;
}
