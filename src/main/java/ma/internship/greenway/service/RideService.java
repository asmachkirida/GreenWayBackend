package ma.internship.greenway.service;

import ma.internship.greenway.dto.PassengerDTO;
import ma.internship.greenway.dto.ReqRes;
import ma.internship.greenway.dto.RideDTO;
import ma.internship.greenway.entity.Car;
import ma.internship.greenway.entity.Passenger;
import ma.internship.greenway.entity.Ride;
import ma.internship.greenway.entity.RidePassenger;
import ma.internship.greenway.repository.CarRepository;
import ma.internship.greenway.repository.PassengerRepository;
import ma.internship.greenway.repository.RidePassengerRepository;
import ma.internship.greenway.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private RidePassengerRepository ridePassengerRepository;

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

        // Fetching passengers
        List<ReqRes> reqResList = ride.getRidePassengers().stream()
                .map(ridePassenger -> {
                    ReqRes reqRes = new ReqRes();
                    Passenger passenger = ridePassenger.getPassenger();
                    reqRes.setFirstName(passenger.getFirstName());
                    reqRes.setLastName(passenger.getLastName());
                    reqRes.setEmail(passenger.getEmail());
                    reqRes.setPhoneNumber(passenger.getPhoneNumber());
                    reqRes.setRole("PASSENGER");
                    reqRes.setCity(passenger.getCity());
                    return reqRes;
                }).collect(Collectors.toList());

        rideDTO.setPassengers(reqResList);
        return rideDTO;
    }


    public List<Ride> filterRides(String startLocation, String endLocation, LocalDate date, Double minPrice, Double maxPrice, Boolean airConditionning, Boolean petAllowed, Integer minDuration, Integer maxDuration) {
        return rideRepository.findAll().stream()
                .filter(r -> (startLocation == null || r.getStartLocation().equalsIgnoreCase(startLocation)) &&
                        (endLocation == null || r.getEndLocation().equalsIgnoreCase(endLocation)) &&
                        (date == null || r.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(date)) &&
                        (minPrice == null || r.getPrice() >= minPrice) &&
                        (maxPrice == null || r.getPrice() <= maxPrice) &&
                        (airConditionning == null || r.isAirConditionning() == airConditionning) &&
                        (petAllowed == null || r.isPetAllowed() == petAllowed) &&
                        (minDuration == null || r.getDuration() >= minDuration) &&
                        (maxDuration == null || r.getDuration() <= maxDuration))
                .collect(Collectors.toList());
    }

    public List<Ride> searchRides(String startLocation, String endLocation, Integer nbrPassengers, LocalDate date) {
        return rideRepository.findAll().stream()
                .filter(r -> (startLocation == null || r.getStartLocation().equalsIgnoreCase(startLocation)) &&
                        (endLocation == null || r.getEndLocation().equalsIgnoreCase(endLocation)) &&
                        (nbrPassengers == null || r.getNbrPassengers() >= nbrPassengers) &&
                        (date == null || r.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(date)))
                .collect(Collectors.toList());
    }

    public void addPassengerToRide(Integer rideId, Integer passengerId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        RidePassenger ridePassenger = new RidePassenger();
        ridePassenger.setRide(ride);
        ridePassenger.setPassenger(passenger);
        ridePassenger.setBookingDate(new Date());
        ridePassenger.setStatus("confirmed");

        ridePassengerRepository.save(ridePassenger);
    }



    public List<RideDTO> getRidesByPassengerId(Integer passengerId) {
        List<RidePassenger> ridePassengers = ridePassengerRepository.findByPassengerId(passengerId);
        return ridePassengers.stream()
                .map(ridePassenger -> convertToDTO(ridePassenger.getRide()))
                .collect(Collectors.toList());
    }

    public List<RideDTO> getRidesByDriverId(Integer driverId) {
        return rideRepository.findByDriverId(driverId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
