package ma.internship.greenway.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverDTO {

    private String firstName;
    private String lastName;
    private String gender;
    private String birthDate;
    private String phoneNumber;
    private String role;
    private String email;
    private String city;
    private String password;
    private String licenseNumber;
    private float rating;
    private String bio;

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
}
