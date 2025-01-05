package com.lightsoutbugsout.tests.business;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import business.Filter;
import objects.Car;
import objects.Dealer;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class FilterTest {
    private Filter filter;
    private ArrayList<String> criteria;
   private ArrayList<Car> cars;
   private final Dealer fake = new Dealer("1"," ","","");

    @Before
    public void setUp() {
        // Initialize with some test criteria.
        criteria = new ArrayList<>(Arrays.asList("Honda","CIVIC","automatic", "gasoline","1","1200000000"));

        cars = new ArrayList<>();
        cars.add(new Car(1, "Toyota", "RAV4", "2012", "24000", "2012", "24000", "automatic","gasoline",fake));
        cars.add(new Car(2, "Honda", "Civic", "2015", "120000", "2015", "15000", "automatic","gasoline",fake));



        filter = new Filter(criteria,cars);
    }

    @Test
    public void testSearchFiltersCorrectly() {
        // Perform search with criteria
        filter.search(criteria);
      
        // Verify the filtered list
        ArrayList<Car> result = filter.getFilterResult();
        assertFalse(result.isEmpty()); // Expect some results based on criteria

        // Validate that all results match the criteria (simplified check)
        for (Car car : result) {
            assertTrue(car.getMake().equalsIgnoreCase(criteria.get(0)));
            // Add more checks for each criteria as needed
        }
    }

    @Test
    public void testFilterResultNotNull() {
        assertNotNull(filter.getFilterResult());
    }

    @Test
    public void testFilterResultIsEmptyWithUnmatchedCriteria() {
        // Setup criteria that matches no car
        ArrayList<String> unmatchedCriteria = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "1", "1"));
        filter.search(unmatchedCriteria);

        assertTrue(filter.getFilterResult().isEmpty());
    }
}

