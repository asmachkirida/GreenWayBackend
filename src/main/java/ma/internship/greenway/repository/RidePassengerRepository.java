package ma.internship.greenway.repository;

import ma.internship.greenway.entity.RidePassenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RidePassengerRepository extends JpaRepository<RidePassenger, Integer> {
    List<RidePassenger> findByPassengerId(Integer passengerId);

}
