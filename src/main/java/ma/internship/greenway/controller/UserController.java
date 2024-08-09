package ma.internship.greenway.controller;


import ma.internship.greenway.dto.DriverDTO;
import ma.internship.greenway.dto.PassengerDTO;
import ma.internship.greenway.dto.ReqRes;
import ma.internship.greenway.entity.User;
import ma.internship.greenway.service.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UsersManagementService usersManagementService;

    @PostMapping("/auth/register-passenger")
    public ResponseEntity<ReqRes> registerPassenger(@RequestBody PassengerDTO reg) {
        return ResponseEntity.ok(usersManagementService.registerPassenger(reg));
    }

    @PostMapping("/auth/register-driver")
    public ResponseEntity<ReqRes> registerDriver(@RequestBody DriverDTO reg) {
        return ResponseEntity.ok(usersManagementService.registerDriver(reg));
    }
    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg){
        return ResponseEntity.ok(usersManagementService.register(reg));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ReqRes> getAllUsers(){
        return ResponseEntity.ok(usersManagementService.getAllUsers());

    }

    @GetMapping("/admin/get-user/{userId}")
    public ResponseEntity<ReqRes> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.ok(usersManagementService.getUserById(userId));
    }


    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Integer userId, @RequestBody User reqres){
        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ReqRes> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ReqRes response = usersManagementService.getMyInfo(email);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<ReqRes> deleteUSer(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }

    @GetMapping("/admin/drivers")
    public List<User> getDrivers() {
        return usersManagementService.getDrivers();
    }

    // Endpoint to get all passengers
    @GetMapping("/admin/passengers")
    public List<User> getPassengers() {
        return usersManagementService.getPassengers();
    }

    @GetMapping("/admin/drivers/search")
    public List<User> searchDrivers(
            @RequestParam(required = false, defaultValue = "") String firstName,
            @RequestParam(required = false, defaultValue = "") String lastName) {
        return usersManagementService.searchDrivers(firstName, lastName);
    }

    // Endpoint to search passengers by first name and last name
    @GetMapping("/admin/passengers/search")
    public List<User> searchPassengers(
            @RequestParam(required = false, defaultValue = "") String firstName,
            @RequestParam(required = false, defaultValue = "") String lastName) {
        return usersManagementService.searchPassengers(firstName, lastName);
    }
}

