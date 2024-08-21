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
@Table(name = "ride_passenger")
public class RidePassenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ride_id", nullable = false)
    @JsonBackReference // Prevents the Ride from being serialized again
    private Ride ride;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    @JsonBackReference // Prevents the Passenger from being serialized again
    private Passenger passenger;

    private Date bookingDate;
    private String status;
}
