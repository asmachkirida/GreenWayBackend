package ma.internship.greenway.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String message;
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    @JsonBackReference // Prevents infinite recursion when serializing Passenger
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    @JsonBackReference // Prevents infinite recursion when serializing Driver
    private Driver driver;

    private boolean isRead;
}
