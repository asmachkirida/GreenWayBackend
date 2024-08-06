package ma.internship.greenway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BikeRideDTO {
    private Integer id;
    private Date date;
    private String startTime;
    private String startLocation;
    private String endLocation;
    private int maxRiders;
    private Integer creatorId;
    private List<GroupRideParticipantsDTO> participants;
}
