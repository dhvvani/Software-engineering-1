package business;


//import android.util.Log;

// Import required classes
import java.util.ArrayList;
import java.util.List;
import objects.Car;


// Favourite
//  Logic Layer
//      Handles the cars that the user favourites in the UI
//      and stores which cars were favourites
public class Favorite {
    private final List<Car> carList;              //contains the list of the cars that are favourites

    // Default constructor
    public Favorite(){
        this.carList = new ArrayList<>();
    }

    // addToFavourite - adds a car to the favourite list
    public List<Car> addToFavorite(Car favorite) {
        boolean alreadyExists = false;

        //check to see if car is already a favourite
        for (Car car : carList) {
            if (car.equals(favorite)) {
                alreadyExists = true;
                break; // Exit the loop as we found a match
            }//if
        }//for

        //if it is not already a favourite then add it.
        if (favorite != null && !alreadyExists) {
            carList.add(favorite);
        }//if

        return carList;
    }// addToFavourite


    // getFavourite - gets the list of all the favourite cars
    public List<Car> getFavorite(){
        return carList;
    }

}// Favourite