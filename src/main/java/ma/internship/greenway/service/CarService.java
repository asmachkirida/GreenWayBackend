package ma.internship.greenway.service;

import ma.internship.greenway.dto.CarDTO;
import ma.internship.greenway.entity.Car;
import ma.internship.greenway.entity.Driver;
import ma.internship.greenway.repository.CarRepository;
import ma.internship.greenway.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private DriverRepository driverRepository;

    public CarDTO addCar(CarDTO carDTO) {
        Driver driver = driverRepository.findById(carDTO.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Car car = new Car();
        car.setModel(carDTO.getModel());
        car.setLicensePlate(carDTO.getLicensePlate());
        car.setCapacity(carDTO.getCapacity());
        car.setBrand(carDTO.getBrand());
        car.setColor(carDTO.getColor());
        car.setDriver(driver);

        Car savedCar = carRepository.save(car);

        return convertToDTO(savedCar);
    }

    public CarDTO updateCar(Integer id, CarDTO carDTO) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        Driver driver = driverRepository.findById(carDTO.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        car.setModel(carDTO.getModel());
        car.setLicensePlate(carDTO.getLicensePlate());
        car.setCapacity(carDTO.getCapacity());
        car.setBrand(carDTO.getBrand());
        car.setColor(carDTO.getColor());
        car.setDriver(driver);

        Car updatedCar = carRepository.save(car);

        return convertToDTO(updatedCar);
    }

    public void deleteCar(Integer id) {
        carRepository.deleteById(id);
    }

    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CarDTO getCarById(Integer id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        return convertToDTO(car);
    }

    public List<CarDTO> getCarsByDriverId(Integer driverId) {
        return carRepository.findByDriverId(driverId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CarDTO convertToDTO(Car car) {
        return new CarDTO(
                car.getId(),
                car.getModel(),
                car.getLicensePlate(),
                car.getCapacity(),
                car.getBrand(),
                car.getColor(),
                car.getDriver().getId()
        );
    }

    public List<CarDTO> searchCars(String brand) {
        List<Car> cars = carRepository.searchByBrand(brand);
        return cars.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
