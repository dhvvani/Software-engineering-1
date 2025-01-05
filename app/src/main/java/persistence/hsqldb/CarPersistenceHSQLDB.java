package persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import business.exceptions.CarNotFoundException;
import business.exceptions.DealerNotFoundException;
import business.exceptions.PersistenceException;
import objects.Car;
import objects.Dealer;
import persistence.CarPersistence;

public class CarPersistenceHSQLDB implements CarPersistence
{
    private final String dbPath;
    private int curIDNum;           //represents the current ID that the last car in the database has
    List<Car> cars;

    public CarPersistenceHSQLDB(final String dbPath)
    {
        this.dbPath = dbPath;
        loadList();
        curIDNum = cars.size();                //the database initially has 7 cars
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }


    //get a list of all cars initially in the database and use it to reflect any changes to the database for quick searching
    private void loadList()
    {
        cars = new ArrayList<>();
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM cars");

            while(rs.next())
            {
                Car curCar = fromResultSet(rs);
                cars.add(curCar);
            }
            rs.close();
            st.close();
        }
        catch (final SQLException e)
        {
            throw new PersistenceException("cannot get car list\n" + e.getMessage());
        }
    }

    /*pass in the result set value and obtain a car from the set*/
    private Car fromResultSet(ResultSet rs) throws SQLException
    {
        int carID = rs.getInt("id");
        String make = rs.getString("make");
        String model = rs.getString("model");
        int year = rs.getInt("year");
        int mileage = rs.getInt("mileage");
        String descrip = rs.getString("description");
        int price = rs.getInt("price");
        String trans = rs.getString("trans");
        String fuelType = rs.getString("fuel_type");
        Dealer owner = getOwnerByCarID(carID);

        return new Car(carID, make, model, Integer.toString(year), String.valueOf(mileage), descrip, String.valueOf(price), trans, fuelType, owner);
    }


    @Override
    public List<Car> getCarList() {
        return cars;
    }

    @Override
    public Car getCarByID(int id) throws CarNotFoundException{

        Iterator<Car> iterator = cars.iterator();

        while(iterator.hasNext())
        {
            Car curCar = iterator.next();
            if(curCar.getId() == id)
                return curCar;
        }

        throw new CarNotFoundException("with id " + id);
    }

    //since getOwnerByCarID is used in fromResultSet() which is further used by loadList(), the function cannot
    //simply iterate through the cars list to find the dealer as it does not reflect current changes to the database
    @Override
    public Dealer getOwnerByCarID(int id) throws DealerNotFoundException{
        try (final Connection c = connection())
        {
            //first identify the id of the dealer for this car from carsdealer
            final PreparedStatement st = c.prepareStatement("SELECT * FROM carsdealers where car_id = ?");
            st.setString(1, Integer.toString(id));

            final ResultSet rs = st.executeQuery();

            int carId = 0;
            int dealer_id = 0;
            if(rs.next())
            {
                carId = rs.getInt("car_id");
                dealer_id = rs.getInt("dealer_id");
            }

            rs.close();
            st.close();

            //get the information of the dealer from dealers
            final PreparedStatement sc = c.prepareStatement("SELECT * FROM dealers where id = ?");
            sc.setString(1, Integer.toString(dealer_id));
            final ResultSet rc = sc.executeQuery();

            //save the dealer's information in variables that will be passed to the dealer constructor
            int dealerID = 0;
            String dealerName = "";
            String address ="";
            String email = "";

            if(rc.next()) {
                dealerID = rc.getInt("id");
                dealerName = rc.getString("name");
                address = rc.getString("number");
                email = rc.getString("email");
            }

            sc.close();
            rc.close();

            //dealer has not been found if no name is available
            if(dealerName.equals(""))
                throw new DealerNotFoundException("dealer dne for car car id: " + id);

            return new Dealer(Integer.toString(dealerID), dealerName, address, email);
        }
        catch(final SQLException e)
        {
            throw new PersistenceException("failed to get the owner by the id of the car: " + id + e.getMessage());
        }
    }

    //determine if a car is in the database already or not
    private Car findCar(Car car)
    {
        for(int i = 0; i < cars.size(); i++)
        {
            Car curCar = cars.get(i);

            if( car.sameTransactionIDs(curCar) )
            {
                System.out.println("car match found");
                return curCar;
            }
        }

        return null;
    }

    //add the car to the car table
    @Override
    public Car addCar(Car car) {

//        determine if an identical car exists in the database
//        if it does, do not add the car to the database again
        Car foundCar = findCar(car);
        if(foundCar != null)
            return foundCar;

        try (final Connection c = connection()) {


          final PreparedStatement st = c.prepareStatement("INSERT INTO cars VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)");

            String[] carDetails = {car.getMake(), car.getModel(), car.getYear(), car.getKm(),
                    car.getDes(), car.getPrice(), car.getTrans(), car.getFuel()};

            //change length to details.length
            for(int i = 0; i < carDetails.length; i++)
            {
                st.setString((i+1), carDetails[i]);
            }

            st.executeUpdate();
            st.close();
            curIDNum++;

            //assign the car an owner
            final PreparedStatement sc = c.prepareStatement("INSERT INTO carsdealers VALUES(?, ?)");
            sc.setString(1, Integer.toString(curIDNum));
            sc.setString(2, car.getOwner().getId());

            sc.executeUpdate();
            sc.close();

            //Return the car with the correct ID
            //the dealerID is correct by the insertDealer method (cars cannot be added if there is no existent dealer)
            Car newCar = new Car(curIDNum, carDetails[0], carDetails[1], carDetails[2], carDetails[3], carDetails[4], carDetails[5], carDetails[6], carDetails[7], car.getOwner());
            cars.add(newCar);
            return newCar;
        }
        catch (final SQLException e) {
            //should throw an exception
            throw new PersistenceException("failed to add car to the database\n"+ e.getMessage());
        }


    }



    @Override
    public void delete(Car car)
    {
        try (final Connection c = connection()) {
            final PreparedStatement sc = c.prepareStatement("DELETE FROM carsdealers WHERE car_id = ?");
            sc.setString(1, Integer.toString(car.getId()));
            sc.executeUpdate();
            sc.close();

            //then delete the dealer themselves
            final PreparedStatement st = c.prepareStatement("DELETE FROM cars WHERE id = ?");
            st.setString(1, Integer.toString(car.getId()));
            st.executeUpdate();
            st.close();


            //delete the car from the cars list if the id is valid
            Iterator<Car> iterator = cars.iterator();

            while(iterator.hasNext())
            {
                Car curCar = iterator.next();
                if(curCar.getId() == car.getId())
                    iterator.remove();
            }

        }
        catch (final SQLException e) {
            //should throw an exception
            throw new PersistenceException("failed to delete car from database");
        }
    }




    @Override
    public void deleteCarByID(int id) {
        try (final Connection c = connection()) {
            //first delete the cars that the dealer owns
            final PreparedStatement sc = c.prepareStatement("DELETE FROM carsdealers WHERE car_id = ?");
            sc.setString(1, Integer.toString(id));
            sc.executeUpdate();
            sc.close();

            //then delete the dealer themselves
            final PreparedStatement st = c.prepareStatement("DELETE FROM cars WHERE id = ?");
            st.setString(1, Integer.toString(id));
            st.executeUpdate();
            st.close();


            //delete the car from the cars list if the id is valid
            Iterator<Car> iterator = cars.iterator();

            while(iterator.hasNext())
            {
                Car curCar = iterator.next();
                if(curCar.getId() == id)
                    iterator.remove();
            }

        } catch (final SQLException e) {
            //should throw an exception
            throw new PersistenceException("failed to delete car from database with id: " + id);
        }

    }

}
