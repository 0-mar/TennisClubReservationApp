package cz.omar.tennisclubreservationapp.auth;

import cz.omar.tennisclubreservationapp.auth.dto.AuthenticationDto;
import cz.omar.tennisclubreservationapp.auth.dto.AuthenticationResponseDto;
import cz.omar.tennisclubreservationapp.auth.dto.RegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /*@PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(
            @RequestBody RegisterDto registerData
    ) {
        return ResponseEntity.ok(authenticationService.register(registerData));
    }*/

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody AuthenticationDto requestBody
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(requestBody));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
