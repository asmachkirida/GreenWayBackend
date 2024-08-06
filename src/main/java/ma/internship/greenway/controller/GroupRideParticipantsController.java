package ma.internship.greenway.controller;

import ma.internship.greenway.dto.GroupRideParticipantsDTO;
import ma.internship.greenway.service.GroupRideParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group-ride-participants")
public class GroupRideParticipantsController {

    @Autowired
    private GroupRideParticipantsService service;

    @PostMapping
    public ResponseEntity<GroupRideParticipantsDTO> addParticipant(@RequestBody GroupRideParticipantsDTO dto) {
        GroupRideParticipantsDTO createdParticipant = service.addParticipant(dto);
        return ResponseEntity.ok(createdParticipant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Integer id) {
        service.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<GroupRideParticipantsDTO>> getAllParticipants() {
        List<GroupRideParticipantsDTO> participants = service.getAllParticipants();
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupRideParticipantsDTO> getParticipantById(@PathVariable Integer id) {
        GroupRideParticipantsDTO participant = service.getParticipantById(id);
        return ResponseEntity.ok(participant);
    }

    @GetMapping("/bike-ride/{bikeRideId}")
    public ResponseEntity<List<GroupRideParticipantsDTO>> getParticipantsByBikeRideId(@PathVariable Integer bikeRideId) {
        List<GroupRideParticipantsDTO> participants = service.getParticipantsByBikeRideId(bikeRideId);
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<GroupRideParticipantsDTO>> getParticipantsByPassengerId(@PathVariable Integer passengerId) {
        List<GroupRideParticipantsDTO> participants = service.getParticipantsByPassengerId(passengerId);
        return ResponseEntity.ok(participants);
    }
}
