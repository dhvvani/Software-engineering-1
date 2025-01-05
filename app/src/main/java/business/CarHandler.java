package business;

import java.util.Collections;
import java.util.List;

import application.Services;
import objects.Car;
import persistence.CarPersistence;

public class CarHandler
{
    private final CarPersistence carPersistence;
    private List<Car> cars;

    public CarHandler()
    {
        carPersistence = Services.getCarPersistence();
    }

    // getAllCars - Returns a list of all the cars from the database
    public List<Car> getAllCars()
    {
        cars = carPersistence.getCarList();
        return Collections.unmodifiableList(cars);
    }


    // getCarByID - Returns the car with the matching carId
    // should throw exception if the car doesn't exist
    public Car getCarByID(int carId) {
        return carPersistence.getCarByID(carId);
    }


    // insertCar - insert a car with car details into the car database
    // should throw an exception for an invalid car
    public Car insertCar(Car car) {
        return carPersistence.addCar(car);
    }

    // deleteCar - deletes the car that matches the vehicle given
    // should throw an exception if the car doesn't exist
    public void deleteCar(Car car)
    {
        carPersistence.delete(car);
    }

    //deleteCarById - delete the car if it has a matching ID in the database
    // should throw an exception if the car doesn't exist
    public void deleteCarById(int carId) {
        carPersistence.deleteCarByID(carId);
    }


}
