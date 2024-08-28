package ma.internship.greenway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Integer id;
    private String message;
    private Date timestamp;
    private boolean isRead;

    private PassengerDTO passenger;
    private DriverDTO driver;
}
