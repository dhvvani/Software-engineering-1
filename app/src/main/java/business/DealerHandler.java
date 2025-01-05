package business;

import java.util.List;

import application.Services;
import objects.Car;
import objects.Dealer;
import persistence.DealerPersistence;

public class DealerHandler
{

    private final DealerPersistence dealerPersistence;
    private List<Dealer> dealers;

    public DealerHandler()
    {
        dealerPersistence = Services.getDealerPersistence();
    }

    //  getAllDealers - Returns a list of all the dealers from the database
    public List<Dealer> getAllDealers() {
        return dealerPersistence.getDealerList();
    }

    // getDealerByID - Returns the dealer with the matching dealerId
    // should throw exception if the dealer doesn't exist
    public Dealer getDealerById(String dealerId) {
        return dealerPersistence.getDealerByID(dealerId);
    }
    // insertDealer - insert a dealer with all dealer details into the dealer database
    // should throw an exception for an invalid dealer
    public Dealer insertDealer(Dealer dealer) {
        return dealerPersistence.addDealer(dealer);
    }

    // deleteDealer - deletes the dealer that matches the dealer given
    // should throw an exception if the dealer doesn't exist
    public void deleteDealer(Dealer dealer) {
        dealerPersistence.deleteDealer(dealer);
    }

    // deleteDealerById - delete the dealer if it has matching ID in the database
    // should throw an exception if the dealer doesn't exist
    public void deleteDealerById(String dealerId) {
        dealerPersistence.deleteDealerByID(dealerId);
    }

    public List<Integer> getCarIDsByDealerID(int id){ return dealerPersistence.getCarIDsByDealerID(id); }

    public List<Car> getCarsByDealerID(int id){ return dealerPersistence.getCarsByDealerID(id); }
}
