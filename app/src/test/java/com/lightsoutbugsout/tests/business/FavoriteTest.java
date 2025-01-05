package com.lightsoutbugsout.tests.business;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import business.Favorite;
import objects.Car;
import objects.Dealer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FavoriteTest {

    private Favorite favorite;
    private Car car1;
    private Car car2;

        @Before
        public void setUp() {
            // Initialize the Favorite object before each test
            favorite = new Favorite();
            Dealer a  = new Dealer("1", "anna", "2047659871", "anna43@gmail.com");
            Dealer b = new Dealer("2", "paul", "2047659871", "john82@gmail.com");

            // Initialize some Car objects with mock data
            // Assuming Car class has an appropriate constructor
            car1 = new Car(1,"Honda","civic","2002","100000", "2002 Honda civic", "19999","automatic","gasoline", a);
            car2 = new Car(2,"Toyota","rav4","2012","10000", "2012 Toyota Rav4", "24000","automatic","gasoline",b);
        }

        @Test
        public void testAddToFavorite() {

            System.out.println("Expected:1");
            // Test adding a car
            favorite.addToFavorite(car1);
            assertEquals(1, favorite.getFavorite().size());
            System.out.println("Output: "+favorite.getFavorite().size());

            // Test adding a duplicate car doesn't increase the list
            favorite.addToFavorite(car1);
            assertEquals(1, favorite.getFavorite().size());

            // Test adding another car
            System.out.println("Expected:2");
            favorite.addToFavorite(car2);
            assertEquals(2, favorite.getFavorite().size());
            System.out.println("Output: "+ favorite.getFavorite().size());
        }


        @Test
        public void testGetFavorite() {
            // Add some cars and test the retrieval
            favorite.addToFavorite(car1);
            favorite.addToFavorite(car2);
            List<Car> favorites = favorite.getFavorite();
            assertTrue(favorites.contains(car1) && favorites.contains(car2));
        }
    }


