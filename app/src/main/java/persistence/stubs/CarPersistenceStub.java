package persistence.stubs;

//Import required classes
import java.util.ArrayList;
import java.util.List;
import business.exceptions.CarNotFoundException;
import business.exceptions.DuplicationException;
import business.exceptions.InvalidDealerException;
import objects.Car;
import objects.Dealer;
import persistence.CarPersistence;
import persistence.DealerPersistence;

//CarPersistenceStub
//  Persistence Layer
//  Implements the stub database for car, implementing the CarPersistence interface
public class CarPersistenceStub implements CarPersistence
{
    private final ArrayList<Car> carDB = new ArrayList<>();

    /*CarPersistenceStub has high coupling with DealerPersistenceStub as the Car class is aggregated with a dealer object.
    * This is prudent technical debt but it is a choice the team is willing to make in order to adhere to the
    * Single-responsibility principle.*/

    //null constructor:
    public CarPersistenceStub()
    {
        DealerPersistenceStub dealer = new DealerPersistenceStub();

        carDB.add(new Car(1,"Honda","civic","2002","100000", "2002 Honda civic", "19999","automatic","gasoline", dealer.getDealerByID("1")));
        carDB.add(new Car(2,"Toyota","rav4","2012","10000", "2012 Toyota Rav4", "24000","automatic","gasoline", dealer.getDealerByID("2")));
        carDB.add(new Car(3,"Toyota","corolla","2012","10000", "2012 Toyota Carolla", "10000","automatic","gasoline", dealer.getDealerByID("3")));
        carDB.add(new Car(4, "Nissan", "note", "2010", "102000", "n/a", "190000", "manual", "gasoline", dealer.getDealerByID("1")));
        carDB.add(new Car(5, "Nissan", "note", "2011", "102000", "n/a", "190000", "manual", "diesel", dealer.getDealerByID("3")));
        carDB.add(new Car(6, "Nissan", "note", "2013", "102000", "n/a", "190000", "manual", "gasoline", dealer.getDealerByID("2")));
        carDB.add(new Car(7, "Hyundai", "electra", "2013", "102000", "n/a", "190000", "automatic", "diesel", dealer.getDealerByID("2")));
    }

    //Dependency constructor
    public CarPersistenceStub(DealerPersistence dealer)
    {
        carDB.add(new Car(1,"Honda","civic","2002","100000", "2002 Honda civic", "19999","automatic","gasoline", dealer.getDealerByID("1")));
        carDB.add(new Car(2,"Toyota","rav4","2012","10000", "2012 Toyota Rav4", "24000","automatic","gasoline", dealer.getDealerByID("2")));
        carDB.add(new Car(3,"Toyota","corolla","2012","10000", "2012 Toyota Carolla", "10000","automatic","gasoline", dealer.getDealerByID("3")));
        carDB.add(new Car(4, "Nissan", "note", "2010", "102000", "n/a", "190000", "manual", "gasoline", dealer.getDealerByID("1")));
        carDB.add(new Car(5, "Nissan", "note", "2011", "102000", "n/a", "190000", "manual", "diesel", dealer.getDealerByID("3")));
        carDB.add(new Car(6, "Nissan", "note", "2013", "102000", "n/a", "190000", "manual", "gasoline", dealer.getDealerByID("2")));
        carDB.add(new Car(7, "Hyundai", "electra", "2013", "102000", "n/a", "190000", "automatic", "diesel", dealer.getDealerByID("2")));
    }

    public List<Car> getCarList()
    {
        return carDB;
    }


    // getCarByID - returns the car with the matching ID
    public Car getCarByID(int id)
    {
        Car retVal = null;

        //Loop till matching car is found
        for(int i = 0; i <carDB.size(); i++)
        {
            int curID = carDB.get(i).getId();
            if( curID == id )
            {
                retVal = carDB.get(i);
                break;
            }

        }

        //if a car with the matching ID was not found
        if (retVal==null){
            throw new CarNotFoundException("Not Found!");
        }
        return retVal;
    }//getCatByID

    // getOwnerByCarID - return the owner by car ID
    public Dealer getOwnerByCarID(int id)
    {
        Dealer owner = getCarByID(id).getOwner();

        //check if car exists
        if (owner != null){
            return owner;
        }
        else{
            throw new InvalidDealerException("Dealer with Car ID not founds");
        }//car not found
    }// getOwnerByCarID

    //setters
    public Car addCar(Car car) throws DuplicationException
    {
        //when adding a car first check if the owner is in the dealer table, if not then add them
        int existingIdx = carDB.indexOf(car);

        //do not allow duplicate entries of cars
        if(existingIdx >= 0 )
        {
            throw new DuplicationException ("Car exists in database");
        }

        carDB.add(car);

        return car;
    }

    // delete - deletes the car from the database if found
    public void delete(Car car)
    {
        int index = carDB.indexOf(car);
        if( index >= 0 ) {
            carDB.remove(index);
        }
        else {
            throw new CarNotFoundException("Not Found!");
        }//not found
    }//delete

    // deleteCarByID - deletes the car by ID
    public void deleteCarByID(int id)
    {
        int curID=-1;
        for(int i = 0; i < carDB.size(); i++)
        {
            curID = carDB.get(i).getId();
            if( curID == id )
            {
                carDB.remove(i);
                break;
            }//if found
        }
        if(curID != id){
            throw new CarNotFoundException("Not Found!");
        }// if not found

    }//deleteCarByID

}//CarPersistenceStub