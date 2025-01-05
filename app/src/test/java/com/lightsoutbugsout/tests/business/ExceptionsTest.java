package com.lightsoutbugsout.tests.business;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;


import business.exceptions.CarNotFoundException;
import business.exceptions.DealerNotFoundException;
import business.exceptions.DuplicationException;
import business.exceptions.InvalidCarException;
import business.exceptions.InvalidDealerException;
import business.exceptions.PersistenceException;

public class ExceptionsTest {

    @Test
    public void testCarNotFoundException() {
        System.out.println(" * Starting Test for Car Not Found Exception");
        boolean caught=false;
        try{
            throw new CarNotFoundException("ERROR");
        }
        catch(CarNotFoundException e){
            caught=true;
        }
        assertTrue(caught);
        System.out.println(" * Finishing Test for Car Not Found Exception");
    }

    @Test
    public void testDealerNotFoundException() {
        System.out.println(" * Starting Test for Dealer Not Found Exception");
        boolean caught=false;
        try{
            throw new DealerNotFoundException("ERROR");
        }
        catch(DealerNotFoundException e){
            caught=true;
        }
        assertTrue(caught);
        System.out.println(" * Finishing Test for Dealer Not Found Exception");
    }

    @Test
    public void testDuplicationException() {
        System.out.println(" * Starting Test for Duplication Exception");
        boolean caught=false;
        try{
            throw new DuplicationException("ERROR");
        }
        catch(DuplicationException e){
            caught=true;
        }
        assertTrue(caught);
        System.out.println(" * Finishing Test for Duplication Exception");
    }

    @Test
    public void testInvalidCarException() {
        System.out.println(" * Starting Test for InvalidCar Exception");
        boolean caught=false;
        try{
            throw new InvalidCarException("ERROR");
        }
        catch(InvalidCarException e){
            caught=true;
        }
        assertTrue(caught);
        System.out.println(" * Finishing Test for InvalidCar Exception");
    }

    @Test
    public void testInvalidDealerException() {
        System.out.println(" * Starting Test for InvalidDealer Exception");
        boolean caught=false;
        try{
            throw new InvalidDealerException("ERROR");
        }
        catch(InvalidDealerException e){
            caught=true;
        }
        assertTrue(caught);
        System.out.println(" * Finishing Test for InvalidDealer Exception");
    }


    @Test
    public void testPersistenceException() {
        System.out.println(" * Starting Test for Persistence Exception");
        boolean caught=false;
        try{
            throw new PersistenceException("ERROR");
        }
        catch(PersistenceException e){
            caught=true;
        }
        assertTrue(caught);
        System.out.println(" * Finishing Test for Persistence Exception");
    }
}
