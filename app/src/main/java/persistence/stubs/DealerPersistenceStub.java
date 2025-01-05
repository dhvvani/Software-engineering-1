package persistence.stubs;

//import required Classes
import java.util.ArrayList;
import java.util.List;
import business.exceptions.DealerNotFoundException;
import business.exceptions.DuplicationException;
import objects.Car;
import objects.Dealer;
import persistence.DealerPersistence;


//DealerPersistenceStub
//  Persistence Layer
//  Implements the stub database for dealers, implementing the DealerPersistence interface
public class DealerPersistenceStub implements DealerPersistence
{
    private final List<Dealer> dealerDB = new ArrayList<>();

    // Constructor
    public DealerPersistenceStub()
    {
        dealerDB.add(new Dealer("1", "anna", "2047659871", "anna43@gmail.com"));
        dealerDB.add(new Dealer("2", "paul", "2047659871", "john82@gmail.com"));
        dealerDB.add(new Dealer("3", "taylor", "2047659871", "swift19@gmail.com"));
    }//stub

    public List<Dealer> getDealerList()
    {
        return dealerDB;
    }

    @Override
    //first delete all cars associated w the ID, then delete the dealer from the dealer list02
    public Dealer getDealerByID(String id)
    {
        String curID="";
        Dealer retVal = null;
        for(int i = 0; i < dealerDB.size(); i++)
        {
            curID = dealerDB.get(i).getId();
            if( curID.equals(id) )
            {
                retVal = dealerDB.get(i);
                break;
            }

        }//go through database to find match
        if (retVal==null){
            throw new DealerNotFoundException("Not Found!");
        }//not found
        return retVal;
    }//getDealerByID


    //addDealer - adds a dealer to the database
    //      performs check to see if it is already in the list
    @Override
    public Dealer addDealer(Dealer dealer) throws DuplicationException
    {
        int existingIdx = dealerDB.indexOf(dealer);

        //do not allow duplicate dealers
        if(existingIdx >= 0 )
        {
            throw new DuplicationException("Dealer already exists in database");
        }
        dealerDB.add(dealer);

        return dealer;
    }//addDealer

    //deleteDealer - deletes the dealer from the list if found
    @Override
    public void deleteDealer(Dealer dealer)
    {

        int index = dealerDB.indexOf(dealer);

        if(index>=0){
            dealerDB.remove(index);
            if(dealerDB.contains(dealer)) {
                throw new DealerNotFoundException("Dealer Not Found!" + index);
            }
            //when deleting a dealer, delete all cars associated w them.
        }

    }//deleteDealer

    //deleteDealerByID - deletes the dealer based on dealerID
    @Override
    public void deleteDealerByID(String id)
    {
        String curID = "";
        for(int i = 0; i < dealerDB.size(); i++)
        {
            curID = String.valueOf(dealerDB.get(i).getId());
            if( curID.equals(id))
            {
                dealerDB.remove(i);
                break;
            }
        }//for
        if(!curID.equals(id)){
            throw new DealerNotFoundException("ID Not Found so cannot delete!");
        }//if not found

    }//deleteDealerByID


    //the following two functions violate the interface segregation principle but it is deliberate debt as the
    // stubs do not use this function but they will be used by the HSQLDB class
    @Override
    public List<Integer> getCarIDsByDealerID(int id)
    {
        //to do this without using a database would require cyclical injection of carPersistence in the constructor
        //so this is not implemented in the stub and is not used anywhere for the stub.
        return null;
    }

    public List<Car> getCarsByDealerID(int id)
    {
        //to do this without using a database would require cyclical injection of carPersistence in the constructor
        //so this is not implemented in the stub and is not used anywhere for the stub.
        return null;
    }


}//DealerPersistenceStub