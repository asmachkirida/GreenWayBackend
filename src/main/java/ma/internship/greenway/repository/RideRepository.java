package ma.internship.greenway.repository;

import ma.internship.greenway.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {
    List<Ride> findByCarId(Integer carId);
}
