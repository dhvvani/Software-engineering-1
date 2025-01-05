package com.lightsoutbugsout.tests.business;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import com.lightsoutbugsout.tests.TestUtils;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import business.CarHandler;
import objects.Car;
import objects.Dealer;

public class CarHandlerIT {
    private CarHandler carHandler;
    Dealer newDealer;
    Car newCar;
    Car delCar;

    private File tempDB;

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        carHandler = new CarHandler();

        newDealer = new Dealer("4", "john", "999999999", "jbrac45@gmail.com");
        newCar = new Car(8, "Hyundai", "electra", "2010", "90",
                "n/a", "380000", "automatic", "gasoline", newDealer);
        delCar = null;
    }

    @After
    public void cleanUp()
    {
        //car inserted has ID 8
        carHandler.deleteCar(newCar);
        if(delCar != null) {
            carHandler.insertCar(delCar);
            delCar = null;
        }
    }

    @Test
    public void testToGetAllCars()
    {
        System.out.println(" * Starting Test to get all cars from Car Handler");
        List<Car> carList =carHandler.getAllCars();
        assertNotNull(carList);
        assertEquals(7, carList.size());
        System.out.println(" * Finished Test to get all cars from Car Handler");
    }


    @Test
    public void testToGetCarById()
    {
        System.out.println(" * Starting Test to get car by ID from Car Handler");
        Car target = carHandler.getCarByID(3);
        assertEquals(3, target.getId());
        System.out.println(" * Finished Test to  get car by ID fromCar Handler");
    }


    @Test
    public void testToInsertCar()
    {
        System.out.println(" * Starting Test to insert car to Car Handler");

        //additionally also test to add duplicates
        newCar = carHandler.insertCar(newCar);
        newCar = carHandler.insertCar(newCar);
        List<Car> actualList = carHandler.getAllCars();
        assertEquals(8, actualList.size());
        System.out.println(" * Finished Test to insert car to Car Handler");

    }

    @Test
    public void testToDeleteCar()
    {
        System.out.println(" * Starting Test to delete car from Car Handler ");
        List<Car> allCars = carHandler.getAllCars();
        int size = allCars.size();
        delCar = allCars.get(size - 1);
        carHandler.deleteCar(delCar);

        List<Car> carList = carHandler.getAllCars();
        assertEquals(6, carList.size());

        System.out.println(" * Finished Test to delete car from Car Handler");
    }

    @Test
    public void testToDeleteCarById()
    {
        System.out.println(" * Starting Test to delete car from by ID Car Handler");
        List<Car> allCars = carHandler.getAllCars();
        int size = allCars.size();
        delCar = allCars.get(size - 1);
        carHandler.deleteCarById(delCar.getId());

        List<Car> carList = carHandler.getAllCars();

        assertEquals(6, carList.size());
        System.out.println(" * Finished Test to delete car from by ID  Car Handler");
    }

}
