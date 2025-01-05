package com.lightsoutbugsout.tests.objects;

import org.junit.Test;

import static org.junit.Assert.*;

import objects.Car;
import objects.Dealer;

//class to test Cars class
public class CarsTest {
    @Test
    public void testCar(){

        System.out.println("\nStarting Cars Object Test");

        //Cars 
        Car[] cars= new Car[3];

        Dealer seller=new Dealer("121","Heisenberg","9999999999","xyz@abc.com");

        cars[0] = new Car(1, "Honda","Accord","2018","21000","** LIKE NEW**","32000","Manual","Diesel",seller);
        cars[1] = new Car(2, "Ford","Mustang","2011","79232","** OLD BUT TAKEN CARE OF**","19000","Manual","Petrol",seller);
        cars[2] = new Car(2, "Ford","Mustang","2011","79232","** OLD BUT TAKEN CARE OF**","19000","Manual","Petrol",seller);

        assertNotNull(cars[1]);
        assertNotEquals(cars[1].getId(),cars[0].getId());
        assertTrue(cars[1].equals(cars[2]));
        assertEquals("Honda",cars[0].getMake());
        assertEquals("Mustang",cars[1].getModel());
        assertEquals("** LIKE NEW**",cars[0].getDes());
        assertEquals("2018",cars[0].getYear());
        assertEquals("21000",cars[0].getKm());
        assertEquals("19000",cars[1].getPrice());
        assertEquals("Manual",cars[0].getTrans());
        assertEquals("Diesel",cars[0].getFuel());
        assertEquals(cars[0].getOwner(),cars[1].getOwner());

        System.out.println("Finished Cars Object Test\n");
    }
}