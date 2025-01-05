package business;

// Import required classes
import objects.Car;
import java.util.ArrayList;
import java.util.List;



// Filter
//  Logic Layer
//  Manages the filters; gets the filtered lists
public class Filter {
    private Car data;
    private final List<Car> list;
   //List that will contain all the cars
    private ArrayList<Car> filteredList;                //List that will only contain the filtered cars


    //Default Constructor
    //Gets the criteria for the Filtering part as an input parameter (Cannot have dependency injection in this part)
    //Since it is meant to be a string
    public Filter(ArrayList<String> criteria, List<Car> cars) {
        this.list = cars; // Use the provided list of cars
        filteredList = new ArrayList<>();

        search(criteria);
    }//constructor

    // search - acts as a search on the criteria so it is not meant to be in the stub
    public void search(ArrayList<String> criteria) {
        // Initialize the filteredList as a new ArrayList
        filteredList = new ArrayList<Car>();

        // Iterate through each car in the original list
        for (Car car : list) {
            boolean matches = criteria.get(0) == null || criteria.get(0).equalsIgnoreCase(car.getMake());

            // Check each criterion, if it's not null and does not match, set matches to false
            if (matches && criteria.get(1) != null && !criteria.get(1).equalsIgnoreCase(car.getModel())) {
                matches = false;
            }
            if (matches && criteria.get(2) != null && !criteria.get(2).equalsIgnoreCase(car.getTrans())) {
                matches = false;
            }
            if (matches && criteria.get(3) != null && !criteria.get(3).equalsIgnoreCase(car.getFuel())) {
                matches = false;
            }
            if (matches && criteria.get(4) != null && Integer.parseInt(criteria.get(4)) > Integer.parseInt(car.getYear())) {
                matches = false;
            }
            if (matches && criteria.get(5) != null && Integer.parseInt(criteria.get(5)) <=Integer.parseInt(car.getKm())) {
                matches = false;
            }

            // If the car matches all the criteria, add it to the filteredList
            if (matches) {
                filteredList.add(car);
            }
        }
    }


    // getFilterResult - gets the result of the filter and returns it
    public ArrayList<Car> getFilterResult(){
        return filteredList;
    }

}//Filter