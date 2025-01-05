package persistence;

//import java.util.ArrayList;
//import objects.Car;

//import required classes
import java.util.List;

import objects.Car;
import objects.Dealer;


// DealerPersistence - Interface
//  Interface for the database of dealers
public interface DealerPersistence
{
    //getters
    List<Dealer> getDealerList();
    Dealer getDealerByID(String id);
    //setters
    Dealer addDealer(Dealer dealer);
    void deleteDealer(Dealer Dealer);
    void deleteDealerByID(String id);

    List<Integer> getCarIDsByDealerID(int id);

    List<Car> getCarsByDealerID(int id);
}
