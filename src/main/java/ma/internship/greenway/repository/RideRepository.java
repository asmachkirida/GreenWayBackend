package ma.internship.greenway.repository;

import ma.internship.greenway.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {
    List<Ride> findByCarId(Integer carId);
    @Query("SELECT r FROM Ride r WHERE r.car.driver.id = :driverId")
    List<Ride> findByDriverId(@Param("driverId") Integer driverId);
}
