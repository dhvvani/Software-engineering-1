package persistence;

import java.util.*;

import objects.Car;
import objects.Dealer;

// CarPersistence - Interface
//  Interface for the database of Cars
public interface CarPersistence
{
    //getters
    List<Car> getCarList();
    Car getCarByID(int id);
    Dealer getOwnerByCarID(int id);

    //setters
    Car addCar(Car car);
    void delete(Car car);
    void deleteCarByID(int id);

}//CarPersistence