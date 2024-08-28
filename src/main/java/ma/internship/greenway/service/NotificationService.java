package ma.internship.greenway.service;

import ma.internship.greenway.dto.NotificationDTO;
import ma.internship.greenway.entity.Driver;
import ma.internship.greenway.entity.Notification;
import ma.internship.greenway.entity.Passenger;
import ma.internship.greenway.repository.DriverRepository;
import ma.internship.greenway.repository.NotificationRepository;
import ma.internship.greenway.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private DriverRepository driverRepository;

    // Method to create a notification
    public NotificationDTO createNotification(Integer passengerId, Integer driverId, String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setTimestamp(new Date());
        notification.setRead(false);

        if (passengerId != null) {
            Passenger passenger = passengerRepository.findById(passengerId)
                    .orElseThrow(() -> new RuntimeException("Passenger not found"));
            notification.setPassenger(passenger);
        }

        if (driverId != null) {
            Driver driver = driverRepository.findById(driverId)
                    .orElseThrow(() -> new RuntimeException("Driver not found"));
            notification.setDriver(driver);
        }

        Notification savedNotification = notificationRepository.save(notification);
        return convertToDTO(savedNotification);
    }

    // Method to get notifications by passenger ID
    public List<NotificationDTO> getNotificationsByPassengerId(Integer passengerId) {
        List<Notification> notifications = notificationRepository.findByPassengerId(passengerId);
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Method to get notifications by driver ID
    public List<NotificationDTO> getNotificationsByDriverId(Integer driverId) {
        List<Notification> notifications = notificationRepository.findByDriverId(driverId);
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Method to mark a notification as read
    public Notification markAsRead(Integer notificationId) {
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setRead(true);
            return notificationRepository.save(notification);
        } else {
            throw new RuntimeException("Notification not found");
        }
    }

    // Helper method to convert Notification entity to DTO
    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setTimestamp(notification.getTimestamp());
        notificationDTO.setRead(notification.isRead());

        if (notification.getPassenger() != null) {
            notificationDTO.setPassengerId(notification.getPassenger().getId());
        }
        if (notification.getDriver() != null) {
            notificationDTO.setDriverId(notification.getDriver().getId());
        }

        return notificationDTO;
    }
}
