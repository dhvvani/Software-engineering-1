package com.lightsoutbugsout.tests.business;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import com.lightsoutbugsout.tests.TestUtils;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import business.CarHandler;
import business.DealerHandler;
import objects.Car;
import objects.Dealer;

public class DealerHandleIT 
{
    private DealerHandler dealerHandler;
    private CarHandler carHandler;
    Dealer newDealer;
    Dealer delDealer;
    List<Car> carsOwned;
    private File tempDB;

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();

        dealerHandler = new DealerHandler();
        carHandler = new CarHandler();
        newDealer = new Dealer("4", "john", "999999999", "jbrac45@gmail.com");
        delDealer = null;
        carsOwned = new ArrayList<>();
        System.out.println("Starting test for Dealer Handler");
    }

    //remove any added dealer (any new dealer added) and re-insert any removed dealer (delDealer)
    //deleted dealers are those that were existing in the database before testing
    //added dealers are newly created and were not in the database before testing
    @After
    public void cleanUp()
    {
        dealerHandler.deleteDealer(newDealer);
        if(delDealer != null)
        {
            dealerHandler.insertDealer(delDealer);

            Iterator<Car> iterator = carsOwned.iterator();
            Car curCar, addCar;

            while( iterator.hasNext() )
            {
                curCar = iterator.next();

                /*create new ownership of the car since the dealer id is different due to default primary key
                use that does not auto-adjust on addition/deletion of cars(or dealers) from the database*/
                addCar = new Car (curCar.getId(), curCar.getMake(), curCar.getModel(), curCar.getYear(),
                        curCar.getKm(), curCar.getDes(), curCar.getPrice(), curCar.getTrans(), curCar.getFuel(),
                        delDealer);

                carHandler.insertCar(addCar);
            }
        }

        List<Dealer> tempList = dealerHandler.getAllDealers();
        Iterator<Dealer> iterator = tempList.iterator();

        while(iterator.hasNext())
        {
            Dealer del = iterator.next();
            System.out.println(del.getName() + " " + del.getId());
        }
    }


    @Test
    public void testToGetAllDealers()
    {
        System.out.println(" * Starting Test to get all Dealers from Dealer Handler");
        List<Dealer> dealerList =dealerHandler.getAllDealers();
        assertNotNull(dealerList);
        assertEquals(3, dealerList.size());
        System.out.println(" * Finished Test to get all Dealers from Dealer Handler");
    }

    @Test
    public void testToGetDealerById()
    {
        System.out.println(" * Starting Test to get dealer by ID from Dealer Handler");
        Dealer target = dealerHandler.getDealerById("1");
        assertEquals("1", target.getId());
        System.out.println(" * Finished Test to get car by ID from Dealer Handler");

    }

    @Test
    public void testToInsertDealer()
    {
        System.out.println(" * Starting Test to insert dealer to Dealer Handler");

        //test to add duplicates as well
        newDealer = dealerHandler.insertDealer(newDealer);
        newDealer = dealerHandler.insertDealer(newDealer);
        List<Dealer> actualList = dealerHandler.getAllDealers();
        assertEquals(4, actualList.size());

        System.out.println(" * Finished Test to insert dealer to Dealer Handler");
    }

    @Test
    public void testToDeleteDealer()
    {
        System.out.println(" * Starting Test to delete dealer from Dealer Handler");

        //delete the dealer added in testToInsertDealer()
        List<Dealer> curDealers = dealerHandler.getAllDealers();
        int size = curDealers.size();
        delDealer = curDealers.get(size - 1);
        int dealerID = Integer.parseInt(delDealer.getId());

        //get the carIDs and the cars owned by this dealer
        carsOwned = dealerHandler.getCarsByDealerID(dealerID);

        //delete the dealer
        dealerHandler.deleteDealer(delDealer);
        List<Dealer> delListAfter = dealerHandler.getAllDealers();

        //assert that the dealer has been removed
        assertEquals(curDealers.size() - 1 , delListAfter.size());


        //obtain the cars the dealer owns after deletion
        List<Car> carIDsNowOwned = dealerHandler.getCarsByDealerID(dealerID);

        //as well as all the cars they owned
        assertEquals(0, carIDsNowOwned.size());

        System.out.println(" * Finished Test to delete dealer from Dealer Handler");
    }

    @Test
    public void testToDeleteDealerById()
    {
        System.out.println(" * Starting Test to delete dealer by ID from Dealer Handler");

        //delete the dealer added in testToInsertDealer()
        List<Dealer> curDealers = dealerHandler.getAllDealers();
        int size = curDealers.size();
        delDealer = curDealers.get(size - 1);

        int dealerID = Integer.parseInt(delDealer.getId());

        //get the carIDs and the cars owned by this dealer
        carsOwned = dealerHandler.getCarsByDealerID(dealerID);
        System.out.println(dealerID);


        //the dealer should've only existed if they had cars to sell on the platform
//        assertNotEquals(0, carsOwned.size());


        //delete the dealer
        dealerHandler.deleteDealerById(delDealer.getId());
        List<Dealer> delListAfter = dealerHandler.getAllDealers();

        //assert that the dealer has been removed
        assertEquals(curDealers.size() - 1 , delListAfter.size());

        //obtain the cars the dealer owns after deletion
        List<Car> carIDsNotOwned = dealerHandler.getCarsByDealerID(dealerID);

        //as well as all the cars they owned
        assertEquals(0, carIDsNotOwned.size());

        System.out.println(" * Finished Test to delete dealer by ID from Dealer Handler");
    }
}
