package ma.internship.greenway.controller;

import ma.internship.greenway.dto.RideDTO;
import ma.internship.greenway.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping
    public ResponseEntity<RideDTO> addRide(@RequestBody RideDTO rideDTO) {
        RideDTO createdRide = rideService.addRide(rideDTO);
        return ResponseEntity.ok(createdRide);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RideDTO> updateRide(@PathVariable Integer id, @RequestBody RideDTO rideDTO) {
        RideDTO updatedRide = rideService.updateRide(id, rideDTO);
        return ResponseEntity.ok(updatedRide);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Integer id) {
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RideDTO>> getAllRides() {
        List<RideDTO> rides = rideService.getAllRides();
        return ResponseEntity.ok(rides);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideDTO> getRideById(@PathVariable Integer id) {
        RideDTO ride = rideService.getRideById(id);
        return ResponseEntity.ok(ride);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<RideDTO>> getRidesByCarId(@PathVariable Integer carId) {
        List<RideDTO> rides = rideService.getRidesByCarId(carId);
        return ResponseEntity.ok(rides);
    }
}
