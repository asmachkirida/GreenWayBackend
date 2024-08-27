package ma.internship.greenway.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String startLocation;
    private String endLocation;
    private Date date;
    private String startTime;
    private String endTime;
    private int duration;
    private float distance;
    private float price;
    private boolean cigaretteAllowed;
    private boolean airConditionning;
    private boolean petAllowed;
    private int nbrPassengers;
    private String status;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RidePassenger> ridePassengers;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
