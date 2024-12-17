package cz.omar.tennisclubreservationapp.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.omar.tennisclubreservationapp.auth.dto.AuthenticationDto;
import cz.omar.tennisclubreservationapp.auth.dto.AuthenticationResponseDto;
import cz.omar.tennisclubreservationapp.auth.dto.RegisterDto;
import cz.omar.tennisclubreservationapp.common.security.JwtService;
import cz.omar.tennisclubreservationapp.token.business.Token;
import cz.omar.tennisclubreservationapp.token.mapper.TokenToDatabaseMapper;
import cz.omar.tennisclubreservationapp.token.storage.TokenRepository;
import cz.omar.tennisclubreservationapp.token.storage.TokenType;
import cz.omar.tennisclubreservationapp.user.business.User;
import cz.omar.tennisclubreservationapp.user.mapper.UserToDatabaseMapper;
import cz.omar.tennisclubreservationapp.user.storage.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        saveUserToken(savedUser, jwtToken, TokenType.ACCESS);
        saveUserToken(savedUser, refreshToken, TokenType.REFRESH);

        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponseDto authenticate(AuthenticationDto requestBody) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                requestBody.getEmail(),
                requestBody.getPassword()
        ));

        var user = (User) authentication.getPrincipal();

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllTokens(user);

        saveUserToken(user, jwtToken, TokenType.ACCESS);
        saveUserToken(user, refreshToken, TokenType.REFRESH);

        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken, TokenType tokenType) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .type(tokenType)
                .build();
        var tok = tokenRepository.create(tokenToDatabaseMapper.tokenToEntity(token));
        System.out.println(tok.getToken());
    }

    private void revokeAllTokens(User user) {
        var validUserTokens = tokenRepository.getAllTokensByUser(user.getId());

        for (var token : validUserTokens) {
            tokenRepository.delete(token.getId());
        }
    }

    private void revokeAccessTokens(User user) {
        var validUserTokens = tokenRepository.getAllTokensByUser(user.getId(), TokenType.ACCESS);

        for (var token : validUserTokens) {
            tokenRepository.delete(token.getId());
        }
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshToken = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(refreshToken);

        var user = repository.getByEmail(userEmail);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not found");
            return;
        }

        var token = tokenRepository.getByToken(refreshToken);

        if (token.getType() != TokenType.REFRESH) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Given token is not a refresh token");
            return;
        }

        var accessToken = jwtService.generateToken(user);
        revokeAccessTokens(user);
        saveUserToken(user, accessToken, TokenType.ACCESS);
        var authResponse = AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);

    }

    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String jwt = authHeader.substring(7);
        var storedToken = tokenRepository.getByToken(jwt);

        revokeAllTokens(storedToken.getUser());

        SecurityContextHolder.clearContext();
    }
}
