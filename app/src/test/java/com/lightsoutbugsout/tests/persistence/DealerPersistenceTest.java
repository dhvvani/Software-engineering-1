package com.lightsoutbugsout.tests.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import objects.Dealer;
import persistence.stubs.DealerPersistenceStub;

public class DealerPersistenceTest 
{

    DealerPersistenceStub dealerStub;
    Dealer newDealer;

    @Before
    public void setup(){
        System.out.println("Starting test for dealerStub Persistence");
        dealerStub = new DealerPersistenceStub();
        newDealer = new Dealer("121","Heisenberg","9999999999","xyz@abc.com");
    }

    @Test
    public void testAddDealerToDatabase(){
        System.out.println(" * Starting Test Add to Dealer dealerStub");

        List<Dealer> listToAdd= dealerStub.getDealerList();
        dealerStub.addDealer(newDealer);
        listToAdd.add(newDealer);

        List<Dealer> addedList= dealerStub.getDealerList();
        assertEquals(addedList,listToAdd);

        System.out.println(" * Finished Test Add to Dealer dealerStub");
    }
    @Test
    public void testGetDealerFromDatabase(){
        System.out.println(" * Starting Test Get Dealer dealerStub");

        List<Dealer> list= dealerStub.getDealerList();
        assertNotNull(list);

        System.out.println(" * Finished Test Get Dealer dealerStub");
    }
    @Test
    public void testDeleteDealerFromDatabase(){
        System.out.println(" * Starting Test delete from Dealer dealerStub");


        Dealer toRemove= dealerStub.getDealerByID(String.valueOf(1));

        List<Dealer> listToRemove= dealerStub.getDealerList();
        listToRemove.remove(toRemove);

        dealerStub.deleteDealer(toRemove);
        List<Dealer> removeList= dealerStub.getDealerList();
        assertEquals(removeList,listToRemove);
        System.out.println(" * Finished Test delete from Dealer dealerStub");
    }


    @After
    public void complete(){
        System.out.println("Finished test for dealerStub Persistence\n");
    }
}
