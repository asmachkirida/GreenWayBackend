package ma.internship.greenway.controller;

import ma.internship.greenway.dto.BikeRideDTO;
import ma.internship.greenway.service.BikeRideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike-rides")
public class BikeRideController {

    @Autowired
    private BikeRideService bikeRideService;

    @PostMapping
    public ResponseEntity<BikeRideDTO> addBikeRide(@RequestBody BikeRideDTO bikeRideDTO) {
        BikeRideDTO createdBikeRide = bikeRideService.addBikeRide(bikeRideDTO);
        return ResponseEntity.ok(createdBikeRide);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BikeRideDTO> updateBikeRide(@PathVariable Integer id, @RequestBody BikeRideDTO bikeRideDTO) {
        BikeRideDTO updatedBikeRide = bikeRideService.updateBikeRide(id, bikeRideDTO);
        return ResponseEntity.ok(updatedBikeRide);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBikeRide(@PathVariable Integer id) {
        bikeRideService.deleteBikeRide(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BikeRideDTO>> getAllBikeRides() {
        List<BikeRideDTO> bikeRides = bikeRideService.getAllBikeRides();
        return ResponseEntity.ok(bikeRides);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BikeRideDTO> getBikeRideById(@PathVariable Integer id) {
        BikeRideDTO bikeRide = bikeRideService.getBikeRideById(id);
        return ResponseEntity.ok(bikeRide);
    }

    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<List<BikeRideDTO>> getBikeRidesByCreatorId(@PathVariable Integer creatorId) {
        List<BikeRideDTO> bikeRides = bikeRideService.getBikeRidesByCreatorId(creatorId);
        return ResponseEntity.ok(bikeRides);
    }
}
