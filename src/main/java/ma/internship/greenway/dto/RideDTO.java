package ma.internship.greenway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideDTO {
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
    private Integer carId;
    private List<ReviewDTO> reviews;
    private List<ReqRes> passengers;


}
