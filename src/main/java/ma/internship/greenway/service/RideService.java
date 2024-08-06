package ma.internship.greenway.service;

import ma.internship.greenway.dto.RideDTO;
import ma.internship.greenway.entity.Car;
import ma.internship.greenway.entity.Ride;
import ma.internship.greenway.repository.CarRepository;
import ma.internship.greenway.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private CarRepository carRepository;

    public RideDTO addRide(RideDTO rideDTO) {
        Car car = carRepository.findById(rideDTO.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        Ride ride = new Ride();
        ride.setStartLocation(rideDTO.getStartLocation());
        ride.setEndLocation(rideDTO.getEndLocation());
        ride.setDate(rideDTO.getDate());
        ride.setStartTime(rideDTO.getStartTime());
        ride.setEndTime(rideDTO.getEndTime());
        ride.setDuration(rideDTO.getDuration());
        ride.setDistance(rideDTO.getDistance());
        ride.setPrice(rideDTO.getPrice());
        ride.setCigaretteAllowed(rideDTO.isCigaretteAllowed());
        ride.setAirConditionning(rideDTO.isAirConditionning());
        ride.setPetAllowed(rideDTO.isPetAllowed());
        ride.setNbrPassengers(rideDTO.getNbrPassengers());
        ride.setStatus(rideDTO.getStatus());
        ride.setCar(car);

        Ride savedRide = rideRepository.save(ride);
        rideDTO.setId(savedRide.getId());
        return rideDTO;
    }

    public RideDTO updateRide(Integer id, RideDTO rideDTO) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setStartLocation(rideDTO.getStartLocation());
        ride.setEndLocation(rideDTO.getEndLocation());
        ride.setDate(rideDTO.getDate());
        ride.setStartTime(rideDTO.getStartTime());
        ride.setEndTime(rideDTO.getEndTime());
        ride.setDuration(rideDTO.getDuration());
        ride.setDistance(rideDTO.getDistance());
        ride.setPrice(rideDTO.getPrice());
        ride.setCigaretteAllowed(rideDTO.isCigaretteAllowed());
        ride.setAirConditionning(rideDTO.isAirConditionning());
        ride.setPetAllowed(rideDTO.isPetAllowed());
        ride.setNbrPassengers(rideDTO.getNbrPassengers());
        ride.setStatus(rideDTO.getStatus());

        Car car = carRepository.findById(rideDTO.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));
        ride.setCar(car);

        Ride updatedRide = rideRepository.save(ride);
        return rideDTO;
    }

    public void deleteRide(Integer id) {
        rideRepository.deleteById(id);
    }

    public List<RideDTO> getAllRides() {
        return rideRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public RideDTO getRideById(Integer id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        return convertToDTO(ride);
    }

    public List<RideDTO> getRidesByCarId(Integer carId) {
        return rideRepository.findByCarId(carId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private RideDTO convertToDTO(Ride ride) {
        RideDTO rideDTO = new RideDTO();
        rideDTO.setId(ride.getId());
        rideDTO.setStartLocation(ride.getStartLocation());
        rideDTO.setEndLocation(ride.getEndLocation());
        rideDTO.setDate(ride.getDate());
        rideDTO.setStartTime(ride.getStartTime());
        rideDTO.setEndTime(ride.getEndTime());
        rideDTO.setDuration(ride.getDuration());
        rideDTO.setDistance(ride.getDistance());
        rideDTO.setPrice(ride.getPrice());
        rideDTO.setCigaretteAllowed(ride.isCigaretteAllowed());
        rideDTO.setAirConditionning(ride.isAirConditionning());
        rideDTO.setPetAllowed(ride.isPetAllowed());
        rideDTO.setNbrPassengers(ride.getNbrPassengers());
        rideDTO.setStatus(ride.getStatus());
        rideDTO.setCarId(ride.getCar().getId());
        return rideDTO;
    }
}
