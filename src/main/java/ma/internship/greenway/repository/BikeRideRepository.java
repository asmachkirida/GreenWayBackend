package ma.internship.greenway.repository;

import ma.internship.greenway.entity.BikeRide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRideRepository extends JpaRepository<BikeRide, Integer> {
    List<BikeRide> findByCreatorId(Integer creatorId);
}
