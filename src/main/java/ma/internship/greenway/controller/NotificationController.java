package ma.internship.greenway.controller;

import ma.internship.greenway.dto.NotificationDTO;
import ma.internship.greenway.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/driver/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Endpoint to create a notification
    @PostMapping("/create")
    public ResponseEntity<NotificationDTO> createNotification(
            @RequestParam Integer passengerId,
            @RequestParam Integer driverId,
            @RequestParam String message) {

        NotificationDTO notificationDTO = notificationService.createNotification(passengerId, driverId, message);
        return ResponseEntity.ok(notificationDTO);
    }

    // Endpoint to get notifications for a specific passenger
    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsForPassenger(@PathVariable Integer passengerId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByPassengerId(passengerId);
        return ResponseEntity.ok(notifications);
    }

    // Endpoint to get notifications for a specific driver
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsForDriver(@PathVariable Integer driverId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByDriverId(driverId);
        return ResponseEntity.ok(notifications);
    }

    // Optional: Endpoint to mark a notification as read
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Integer notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.noContent().build();
    }
}
