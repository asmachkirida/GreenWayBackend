package ma.internship.greenway.repository;

import ma.internship.greenway.entity.GroupRideParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRideParticipantsRepository extends JpaRepository<GroupRideParticipants, Integer> {
    List<GroupRideParticipants> findByBikeRideId(Integer bikeRideId);
    List<GroupRideParticipants> findByPassengerId(Integer passengerId);
}
