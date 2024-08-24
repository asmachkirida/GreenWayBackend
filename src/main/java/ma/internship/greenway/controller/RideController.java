package ma.internship.greenway.controller;

import ma.internship.greenway.dto.RideDTO;
import ma.internship.greenway.entity.Ride;
import ma.internship.greenway.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @PostMapping("/filter")
    public List<Ride> filterRides(@RequestParam(required = false) String startLocation,
                                  @RequestParam(required = false) String endLocation,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                  @RequestParam(required = false) Double minPrice,
                                  @RequestParam(required = false) Double maxPrice,
                                  @RequestParam(required = false) Boolean airConditionning,
                                  @RequestParam(required = false) Boolean petAllowed,
                                  @RequestParam(required = false) Integer minDuration,
                                  @RequestParam(required = false) Integer maxDuration) {
        return rideService.filterRides(startLocation, endLocation, date, minPrice, maxPrice, airConditionning, petAllowed, minDuration, maxDuration);
    }

    @GetMapping("/search")
    public List<Ride> searchRides(@RequestParam(required = false) String startLocation,
                                  @RequestParam(required = false) String endLocation,
                                  @RequestParam(required = false) Integer nbrPassengers,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return rideService.searchRides(startLocation, endLocation, nbrPassengers, date);
    }

    @PostMapping("/{rideId}/passengers")
    public ResponseEntity<Void> addPassengerToRide(@PathVariable Integer rideId, @RequestBody Integer passengerId) {
        rideService.addPassengerToRide(rideId, passengerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<RideDTO>> getRidesByPassengerId(@PathVariable Integer passengerId) {
        List<RideDTO> rides = rideService.getRidesByPassengerId(passengerId);
        return ResponseEntity.ok(rides);
    };

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<RideDTO>> getRidesByDriverId(@PathVariable Integer driverId) {
        List<RideDTO> rides = rideService.getRidesByDriverId(driverId);
        return ResponseEntity.ok(rides);
    }

}
