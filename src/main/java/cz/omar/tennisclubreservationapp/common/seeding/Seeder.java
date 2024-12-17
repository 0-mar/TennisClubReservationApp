package cz.omar.tennisclubreservationapp.common.seeding;

import cz.omar.tennisclubreservationapp.auth.service.AuthenticationService;
import cz.omar.tennisclubreservationapp.auth.dto.RegisterDto;
import cz.omar.tennisclubreservationapp.court.storage.CourtEntity;
import cz.omar.tennisclubreservationapp.court.storage.CourtRepository;
import cz.omar.tennisclubreservationapp.surface.business.Surface;
import cz.omar.tennisclubreservationapp.surface.mappers.SurfaceToDatabaseMapper;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceEntity;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cz.omar.tennisclubreservationapp.user.storage.Role.ADMIN;
import static cz.omar.tennisclubreservationapp.user.storage.Role.USER;

@RequiredArgsConstructor
@Component
public class Seeder {

    @Value("${seed-data}")
    private boolean isDataInitializationEnabled;

    private final SurfaceRepository surfaceRepository;
    private final CourtRepository courtRepository;
    private final SurfaceToDatabaseMapper surfaceToDatabaseMapper;
    private final AuthenticationService service;


    @Bean
    public ApplicationRunner initializeData() {
        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) {
                if (isDataInitializationEnabled) {
                    System.out.println("Seeding data...");
                    initializeSurfaceEntities();
                    initializeCourtEntities();
                    initializeUsers();
                    System.out.println("Seeding was successful!");
                }
            }
        };
    }

    private void initializeSurfaceEntities() {
        SurfaceEntity grass = new SurfaceEntity();
        grass.setName("Grass");
        grass.setRentPerMinute(80f);

        SurfaceEntity clay = new SurfaceEntity();
        clay.setName("Clay");
        clay.setRentPerMinute(60f);

        surfaceRepository.create(grass);
        surfaceRepository.create(clay);
    }

    private void initializeCourtEntities() {
        List<Surface> surfaces = surfaceRepository.getAll();

        CourtEntity court1 = new CourtEntity();
        court1.setName("Court 1");
        court1.setSurfaceEntity(surfaceToDatabaseMapper.surfaceToEntity(surfaces.get(0)));

        CourtEntity court2 = new CourtEntity();
        court2.setName("Court 2");
        court2.setSurfaceEntity(surfaceToDatabaseMapper.surfaceToEntity(surfaces.get(1)));

        CourtEntity court3 = new CourtEntity();
        court3.setName("Court 3");
        court3.setSurfaceEntity(surfaceToDatabaseMapper.surfaceToEntity(surfaces.get(0)));

        CourtEntity court4 = new CourtEntity();
        court4.setName("Court 4");
        court4.setSurfaceEntity(surfaceToDatabaseMapper.surfaceToEntity(surfaces.get(1)));

        courtRepository.create(court1);
        courtRepository.create(court2);
        courtRepository.create(court3);
        courtRepository.create(court4);
    }

    private void initializeUsers() {
        var admin = RegisterDto.builder()
                .email("admin@mail.com")
                .password("password")
                .role(ADMIN)
                .build();
        var adminData = service.register(admin);
        System.out.println("Admin token: " + adminData.getAccessToken());
        System.out.println("Admin refresh token: " + adminData.getRefreshToken());

        var user = RegisterDto.builder()
                .email("user@mail.com")
                .password("password")
                .role(USER)
                .build();

        var userData = service.register(user);
        System.out.println("User token: " + userData.getAccessToken());
        System.out.println("User refresh token: " + userData.getRefreshToken());
    }
}
