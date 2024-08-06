package ma.internship.greenway.service;

import ma.internship.greenway.dto.BikeRideDTO;
import ma.internship.greenway.entity.BikeRide;
import ma.internship.greenway.entity.Passenger;
import ma.internship.greenway.repository.BikeRideRepository;
import ma.internship.greenway.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BikeRideService {

    @Autowired
    private BikeRideRepository bikeRideRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    public BikeRideDTO addBikeRide(BikeRideDTO bikeRideDTO) {
        Passenger creator = passengerRepository.findById(bikeRideDTO.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        BikeRide bikeRide = new BikeRide();
        bikeRide.setDate(bikeRideDTO.getDate());
        bikeRide.setStartTime(bikeRideDTO.getStartTime());
        bikeRide.setStartLocation(bikeRideDTO.getStartLocation());
        bikeRide.setEndLocation(bikeRideDTO.getEndLocation());
        bikeRide.setMaxRiders(bikeRideDTO.getMaxRiders());
        bikeRide.setCreator(creator);

        BikeRide savedBikeRide = bikeRideRepository.save(bikeRide);
        bikeRideDTO.setId(savedBikeRide.getId());
        return bikeRideDTO;
    }

    public BikeRideDTO updateBikeRide(Integer id, BikeRideDTO bikeRideDTO) {
        BikeRide bikeRide = bikeRideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BikeRide not found"));

        bikeRide.setDate(bikeRideDTO.getDate());
        bikeRide.setStartTime(bikeRideDTO.getStartTime());
        bikeRide.setStartLocation(bikeRideDTO.getStartLocation());
        bikeRide.setEndLocation(bikeRideDTO.getEndLocation());
        bikeRide.setMaxRiders(bikeRideDTO.getMaxRiders());

        Passenger creator = passengerRepository.findById(bikeRideDTO.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        bikeRide.setCreator(creator);

        BikeRide updatedBikeRide = bikeRideRepository.save(bikeRide);
        return bikeRideDTO;
    }

    public void deleteBikeRide(Integer id) {
        bikeRideRepository.deleteById(id);
    }

    public List<BikeRideDTO> getAllBikeRides() {
        return bikeRideRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BikeRideDTO getBikeRideById(Integer id) {
        BikeRide bikeRide = bikeRideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BikeRide not found"));
        return convertToDTO(bikeRide);
    }

    public List<BikeRideDTO> getBikeRidesByCreatorId(Integer creatorId) {
        return bikeRideRepository.findByCreatorId(creatorId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private BikeRideDTO convertToDTO(BikeRide bikeRide) {
        BikeRideDTO bikeRideDTO = new BikeRideDTO();
        bikeRideDTO.setId(bikeRide.getId());
        bikeRideDTO.setDate(bikeRide.getDate());
        bikeRideDTO.setStartTime(bikeRide.getStartTime());
        bikeRideDTO.setStartLocation(bikeRide.getStartLocation());
        bikeRideDTO.setEndLocation(bikeRide.getEndLocation());
        bikeRideDTO.setMaxRiders(bikeRide.getMaxRiders());
        bikeRideDTO.setCreatorId(bikeRide.getCreator().getId());
        return bikeRideDTO;
    }
}
