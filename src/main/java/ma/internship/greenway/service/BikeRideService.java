package ma.internship.greenway.service;

import jakarta.transaction.Transactional;
import ma.internship.greenway.dto.BikeRideDTO;
import ma.internship.greenway.dto.ReqRes;
import ma.internship.greenway.entity.BikeRide;
import ma.internship.greenway.entity.GroupRideParticipants;
import ma.internship.greenway.entity.Passenger;
import ma.internship.greenway.repository.BikeRideRepository;
import ma.internship.greenway.repository.GroupRideParticipantsRepository;
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

    @Autowired
    private GroupRideParticipantsRepository groupRideParticipantsRepository;

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

        // Mapping participants to ReqRes
        List<ReqRes> participants = bikeRide.getParticipants().stream()
                .map(groupRideParticipant -> {
                    ReqRes reqRes = new ReqRes();
                    Passenger passenger = groupRideParticipant.getPassenger(); // Assuming GroupRideParticipant has a Passenger field
                    reqRes.setFirstName(passenger.getFirstName());
                    reqRes.setLastName(passenger.getLastName());
                    reqRes.setEmail(passenger.getEmail());
                    reqRes.setPhoneNumber(passenger.getPhoneNumber());
                    reqRes.setRole("PASSENGER"); // Set the role as needed
                    reqRes.setCity(passenger.getCity());
                    // Set other fields of ReqRes as needed
                    return reqRes;
                }).collect(Collectors.toList());

        bikeRideDTO.setParticipants(participants);
        return bikeRideDTO;
    }


    @Transactional
    public void addParticipantToBikeRide(Integer bikeRideId, Integer passengerId) {
        // Fetch the BikeRide entity
        BikeRide bikeRide = bikeRideRepository.findById(bikeRideId)
                .orElseThrow(() -> new IllegalArgumentException("Bike ride not found with ID: " + bikeRideId));

        // Fetch the Passenger entity
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalArgumentException("Passenger not found with ID: " + passengerId));

        // Create and save the new participant
        GroupRideParticipants participant = new GroupRideParticipants();
        participant.setBikeRide(bikeRide); // Set the BikeRide entity
        participant.setPassenger(passenger); // Set the Passenger entity

        groupRideParticipantsRepository.save(participant);
    }
}
