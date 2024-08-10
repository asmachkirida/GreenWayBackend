package ma.internship.greenway.repository;

import ma.internship.greenway.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByDriverId(Integer driverId);
    @Query("SELECT c FROM Car c WHERE (:brand IS NULL OR c.brand LIKE %:brand%)")
    List<Car> searchByBrand(@Param("brand") String brand);
}
