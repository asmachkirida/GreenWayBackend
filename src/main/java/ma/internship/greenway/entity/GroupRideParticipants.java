package ma.internship.greenway.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grouprideparticipants")
public class GroupRideParticipants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    @JsonBackReference
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "bikeride_id", nullable = false)
    private BikeRide bikeRide;
}
