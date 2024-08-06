package ma.internship.greenway.service;

import ma.internship.greenway.dto.GroupRideParticipantsDTO;
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
public class GroupRideParticipantsService {

    @Autowired
    private GroupRideParticipantsRepository groupRideParticipantsRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private BikeRideRepository bikeRideRepository;

    public GroupRideParticipantsDTO addParticipant(GroupRideParticipantsDTO dto) {
        Passenger passenger = passengerRepository.findById(dto.getPassengerId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        BikeRide bikeRide = bikeRideRepository.findById(dto.getBikeRideId())
                .orElseThrow(() -> new RuntimeException("BikeRide not found"));

        GroupRideParticipants participant = new GroupRideParticipants();
        participant.setPassenger(passenger);
        participant.setBikeRide(bikeRide);

        GroupRideParticipants savedParticipant = groupRideParticipantsRepository.save(participant);
        dto.setId(savedParticipant.getId());
        return dto;
    }

    public void deleteParticipant(Integer id) {
        groupRideParticipantsRepository.deleteById(id);
    }

    public List<GroupRideParticipantsDTO> getAllParticipants() {
        return groupRideParticipantsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public GroupRideParticipantsDTO getParticipantById(Integer id) {
        GroupRideParticipants participant = groupRideParticipantsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Participant not found"));
        return convertToDTO(participant);
    }

    public List<GroupRideParticipantsDTO> getParticipantsByBikeRideId(Integer bikeRideId) {
        return groupRideParticipantsRepository.findByBikeRideId(bikeRideId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<GroupRideParticipantsDTO> getParticipantsByPassengerId(Integer passengerId) {
        return groupRideParticipantsRepository.findByPassengerId(passengerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private GroupRideParticipantsDTO convertToDTO(GroupRideParticipants participant) {
        return new GroupRideParticipantsDTO(
                participant.getId(),
                participant.getPassenger().getId(),
                participant.getBikeRide().getId()
        );
    }
}
