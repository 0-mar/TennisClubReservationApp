package cz.omar.tennisclubreservationapp;

import cz.omar.tennisclubreservationapp.court.business.Court;
import cz.omar.tennisclubreservationapp.court.storage.CourtEntity;
import cz.omar.tennisclubreservationapp.court.storage.CourtRepository;
import cz.omar.tennisclubreservationapp.surface.mappers.SurfaceToDatabaseMapper;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceDao;
import cz.omar.tennisclubreservationapp.surface.storage.SurfaceEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TennisClubReservationAppApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TennisClubReservationAppApplication.class, args);

        SurfaceToDatabaseMapper surfaceToDatabaseMapper = context.getBean(SurfaceToDatabaseMapper.class);
        SurfaceDao surfaceDao = context.getBean(SurfaceDao.class);
        SurfaceEntity surfaceEntity = new SurfaceEntity();
        surfaceEntity.setName("clay");
        surfaceEntity.setRentPerMinute(15.5f);
        surfaceDao.create(surfaceEntity);

        CourtRepository courtRepository = context.getBean(CourtRepository.class);
        CourtEntity courtEntity = new CourtEntity();
        courtEntity.setName("Wembley 1");
        courtEntity.setSurfaceEntity(surfaceEntity);
        Court newCourt = courtRepository.create(courtEntity);

        System.out.println("Court created: " + newCourt);
    }

}
