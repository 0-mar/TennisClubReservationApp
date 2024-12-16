package cz.omar.tennisclubreservationapp.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.omar.tennisclubreservationapp.auth.dto.AuthenticationDto;
import cz.omar.tennisclubreservationapp.auth.dto.AuthenticationResponseDto;
import cz.omar.tennisclubreservationapp.auth.dto.RegisterDto;
import cz.omar.tennisclubreservationapp.common.security.JwtService;
import cz.omar.tennisclubreservationapp.token.business.Token;
import cz.omar.tennisclubreservationapp.token.mapper.TokenToDatabaseMapper;
import cz.omar.tennisclubreservationapp.token.storage.TokenRepository;
import cz.omar.tennisclubreservationapp.user.business.User;
import cz.omar.tennisclubreservationapp.user.mapper.UserToDatabaseMapper;
import cz.omar.tennisclubreservationapp.user.storage.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenToDatabaseMapper tokenToDatabaseMapper;
    private final UserToDatabaseMapper userToDatabaseMapper;

    public AuthenticationResponseDto register(RegisterDto registerData) {
        var user = User.builder()
                .email(registerData.getEmail())
                .password(passwordEncoder.encode(registerData.getPassword()))
                .role(registerData.getRole())
                .build();
        var savedUser = repository.create(userToDatabaseMapper.userToEntity(user));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponseDto authenticate(AuthenticationDto requestBody) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                requestBody.getEmail(),
                requestBody.getPassword()
        ));

        var user = repository.getByEmail(requestBody.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.create(tokenToDatabaseMapper.tokenToEntity(token));
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.getAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        for (var token : validUserTokens) {
            tokenRepository.create(tokenToDatabaseMapper.tokenToEntity(token));
        }
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.getByEmail(userEmail);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
