package ma.internship.greenway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRideParticipantsDTO {
    private Integer id;
    private Integer passengerId;
    private Integer bikeRideId;
}
