package ma.internship.greenway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String model;
    private String licensePlate;
    private int capacity;
    private String brand;
    private String color;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;


}
