package ma.internship.greenway.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity(name = "bikeride")
@NoArgsConstructor
@AllArgsConstructor
public class BikeRide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;
    private String startTime;
    private String startLocation;
    private String endLocation;
    private int maxRiders;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonBackReference
    private Passenger creator;

    @OneToMany(mappedBy = "bikeRide", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupRideParticipants> participants;
}
