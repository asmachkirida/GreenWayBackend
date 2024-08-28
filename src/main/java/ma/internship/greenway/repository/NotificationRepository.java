package ma.internship.greenway.repository;

import ma.internship.greenway.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByPassengerId(Integer passengerId);
    List<Notification> findByDriverId(Integer driverId);
}
