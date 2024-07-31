package ma.internship.greenway.service;


import ma.internship.greenway.dto.DriverDTO;
import ma.internship.greenway.dto.PassengerDTO;
import ma.internship.greenway.dto.ReqRes;
import ma.internship.greenway.entity.Driver;
import ma.internship.greenway.entity.Passenger;
import ma.internship.greenway.entity.User;
import ma.internship.greenway.repository.DriverRepository;
import ma.internship.greenway.repository.PassengerRepository;
import ma.internship.greenway.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
public class UsersManagementService {

    @Autowired
    private UserRepo usersRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private DriverRepository driverRepository;

    public ReqRes registerDriver(DriverDTO registrationRequest) {
        ReqRes resp = new ReqRes();

        try {
            Driver driver = new Driver();
            driver.setEmail(registrationRequest.getEmail());
            driver.setGender(registrationRequest.getGender());
            driver.setPhoneNumber(registrationRequest.getPhoneNumber());
            driver.setFirstName(registrationRequest.getFirstName());
            driver.setLastName(registrationRequest.getLastName());

            // Convert birth date string to Date object
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = dateFormat.parse(registrationRequest.getBirthDate());
            driver.setBirthDate(birthDate);

            driver.setRole(registrationRequest.getRole());
            driver.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            driver.setCity(registrationRequest.getCity());
            driver.setLicenseNumber(registrationRequest.getLicenseNumber());
            driver.setRating(registrationRequest.getRating());
            driver.setBio(registrationRequest.getBio());

            // Save the driver
            Driver savedDriver = driverRepository.save(driver);

            if (savedDriver.getId() != null) {
                String token = jwtUtils.generateToken(savedDriver);
                resp.setOurUsers(savedDriver);
                resp.setMessage("Driver Registered Successfully");
                resp.setStatusCode(200);
                resp.setToken(token);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes registerPassenger(PassengerDTO registrationRequest) {
        ReqRes resp = new ReqRes();

        try {
            Passenger passenger = new Passenger();
            passenger.setEmail(registrationRequest.getEmail());
            passenger.setGender(registrationRequest.getGender());
            passenger.setPhoneNumber(registrationRequest.getPhoneNumber());
            passenger.setFirstName(registrationRequest.getFirstName());
            passenger.setLastName(registrationRequest.getLastName());

            // Convert birth date string to Date object
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = dateFormat.parse(registrationRequest.getBirthDate());
            passenger.setBirthDate(birthDate);

            passenger.setRole(registrationRequest.getRole());
            passenger.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            passenger.setCity(registrationRequest.getCity());
            passenger.setMembership(registrationRequest.getMembership());

            // Save the passenger
            Passenger savedPassenger = passengerRepository.save(passenger);

            if (savedPassenger.getId() != null) {
                String token = jwtUtils.generateToken(savedPassenger);
                resp.setOurUsers(savedPassenger);

                resp.setMessage("Passenger Registered Successfully");
                resp.setStatusCode(200);
                resp.setToken(token);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }



    public ReqRes register(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();

        try {
            User ourUser = new User();
            ourUser.setEmail(registrationRequest.getEmail());
            ourUser.setGender(registrationRequest.getGender());
            ourUser.setPhoneNumber(registrationRequest.getPhoneNumber());
            ourUser.setFirstName(registrationRequest.getFirstName());
            ourUser.setLastName(registrationRequest.getLastName());

            // Convert birth date string to Date object
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = dateFormat.parse(registrationRequest.getBirthDate());
            ourUser.setBirthDate(birthDate);

            ourUser.setRole(registrationRequest.getRole());
            ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUser.setCity(registrationRequest.getCity());

            // Save the user
            User ourUsersResult = usersRepo.save(ourUser);

            if (ourUsersResult.getId() > 0) {
                // Generate JWT token
                String token = jwtUtils.generateToken(ourUsersResult);
                resp.setOurUsers(ourUsersResult);

                // Set response
                resp.setStatusCode(200);
                resp.setMessage("User Saved Successfully");
                resp.setToken(token);  // Set the token in the response
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }



    public ReqRes login(ReqRes loginRequest){
        ReqRes response = new ReqRes();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }





    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
        try{
            String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
            User users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqiest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }


    public ReqRes getAllUsers() {
        ReqRes reqRes = new ReqRes();

        try {
            List<User> result = usersRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setOurUsersList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }


    public ReqRes getUsersById(Integer id) {
        ReqRes reqRes = new ReqRes();
        try {
            User usersById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setOurUsers(usersById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes deleteUser(Integer userId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<User> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes updateUser(Integer userId, User updatedUser) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<User> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());

                existingUser.setFirstName(updatedUser.getFirstName());
                existingUser.setLastName(updatedUser.getLastName());
                existingUser.setBirthDate(updatedUser.getBirthDate());
                existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
                existingUser.setRole(updatedUser.getRole());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode the password and update it
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                User savedUser = usersRepo.save(existingUser);
                reqRes.setOurUsers(savedUser);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes getMyInfo(String email){
        ReqRes reqRes = new ReqRes();
        try {
            Optional<User> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setOurUsers(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }
}
