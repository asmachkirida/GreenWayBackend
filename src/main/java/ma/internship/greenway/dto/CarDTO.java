package ma.internship.greenway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Integer id;
    private String model;
    private String licensePlate;
    private int capacity;
    private String brand;
    private String color;
    private Integer driverId;
}
