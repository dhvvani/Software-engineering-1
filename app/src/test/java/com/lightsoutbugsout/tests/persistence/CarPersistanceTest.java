package com.lightsoutbugsout.tests.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import objects.Car;
import objects.Dealer;
import persistence.stubs.CarPersistenceStub;
import persistence.stubs.DealerPersistenceStub;

public class CarPersistanceTest
{
    CarPersistenceStub database;
    DealerPersistenceStub dealerStub;
    Dealer newDealer;
    Car newCar;
    @Before
    public void setup(){
        System.out.println("Starting test for Database Persistence");
        dealerStub = new DealerPersistenceStub();
        database = new CarPersistenceStub(dealerStub);
        newDealer = new Dealer("121","Heisenberg","9999999999","xyz@abc.com");
        newCar = new Car(8, "Honda","Accord","2018","21000","** LIKE NEW**","32000","Manual","Diesel",newDealer);
    }
    @Test
    public void testAddCarToDatabase(){
        System.out.println(" * Starting Test Add to Car Database");

        List<Car> list= database.getCarList();

        database.addCar(newCar);
        list.add(newCar);


        List<Car> addedList = database.getCarList();
        assertEquals(addedList,list);
        System.out.println(" * Finished Test Add to Car Database");
    }
    @Test
    public void testGetCarListFromDatabase(){
        System.out.println(" * Starting Test Get Car Database");

        List<Car> list= database.getCarList();
        assertNotNull(list);
        System.out.println(" * Finished Test Get Car Database");
    }
    @Test
    public void testDeleteCarFromDatabase(){
        System.out.println(" * Starting Test delete from Car Database");

        Car toRemove= database.getCarByID(1);
        List<Car> listToRemove= database.getCarList();

        database.delete(toRemove);
        listToRemove.remove(toRemove);

        List<Car> removeList= database.getCarList();
        assertEquals(removeList,listToRemove);

        System.out.println(" * Finished Test delete from Car Database");
    }
}
