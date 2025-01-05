package com.lightsoutbugsout.tests.business;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import business.CarFilterManager;
import business.CarHandler;
import business.Filter;
import objects.Car;
import objects.Dealer;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertTrue;

public class CarFilterManagerTest {

    private final Dealer fake = new Dealer("1"," ","","");
    private CarFilterManager manager;
    private List<Car> testCars;
    private CarHandler mockData;

    @Before
    public void setUp() {
        mockData = Mockito.mock(CarHandler.class);

        testCars = Arrays.asList(
                new Car(1, "Toyota", "RAV4", "2012", "24000", "2012", "24000", "automatic","gasoline",fake),
                new Car(2, "Honda", "Civic", "2015", "120000", "2015", "15000", "automatic","gasoline",fake)
                );

        when(mockData.getAllCars()).thenReturn(testCars);
        manager = new CarFilterManager(mockData);
    }

    @Test
    public void testGetDistinctMakes() {
        List<String> makes = manager.getDistinctMakes();
         assertEquals(2, makes.size());
         assertTrue(makes.containsAll(Arrays.asList("Toyota", "Honda")));

    }

    @Test
    public void testGetDistinctTransmissions() {
        List<String> transmissions = manager.getDistinctTransmissions();
        assertEquals(1, transmissions.size());
        assertTrue(transmissions.containsAll(Collections.singletonList("automatic")));
    }

    @Test
    public void testGetDistinctFuels() {
        List<String> fuels = manager.getDistinctFuels();
        assertEquals(1, fuels.size());
        assertTrue(fuels.containsAll(Collections.singletonList("gasoline")));
    }

    @Test
    public void testGetModelsForMake() {
        List<String> toyotaModels = manager.getModelsForMake("Toyota");
        assertEquals(1, toyotaModels.size());
        assertTrue(toyotaModels.containsAll(Collections.singletonList("RAV4")));

    }
}