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

import business.exceptions.DealerNotFoundException;
import business.exceptions.PersistenceException;
import objects.Car;
import objects.Dealer;
import persistence.DealerPersistence;

public class DealerPersistenceHSQLDB implements DealerPersistence
{
    private final String dbPath;
    private int curIDNum = 0;           //represents the current ID that the last car in the database has
    private List<Dealer> dealers;

    public DealerPersistenceHSQLDB(final String dbPath)
    {
        this.dbPath = dbPath;
        dealers = new ArrayList<>();
        curIDNum = 3;                   //the database initially has 3 dealers
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }


    //returns a dealer from the current pointer of the result set
    private Dealer fromResultSet(final ResultSet rs) throws SQLException {
        final int dealerID = rs.getInt("id");
        final String dealerName = rs.getString("name");
        final String address = rs.getString("number");
        final String email = rs.getString("email");

        return new Dealer(Integer.toString(dealerID), dealerName, address, email);
    }

    //gets a list of all the dealers in the database
    @Override
    public List<Dealer> getDealerList()
    {
        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            dealers = new ArrayList<>();
            final ResultSet rs = st.executeQuery("SELECT * FROM dealers");

            while (rs.next())
            {
                final Dealer dealer = fromResultSet(rs);
                dealers.add(dealer);
            }

            rs.close();
            st.close();

            return dealers;
        } catch (final SQLException e) {
            //should throw an exception
            throw new PersistenceException("failed to get dealers in the database");
        }
    }

    //gets a dealer with a specific ID
    @Override
    public Dealer getDealerByID(String id) throws DealerNotFoundException
    {
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM dealers where id = ?");
            st.setString(1, id);

            final ResultSet rs = st.executeQuery();
            Dealer desiredDealer = null;
            if(rs.next())
                desiredDealer = fromResultSet(rs);

            rs.close();
            st.close();

            //should throw an exception if the desired dealer was not found
            if(desiredDealer == null)
                throw new DealerNotFoundException("dealer with id: " + id);

            return desiredDealer;
        }catch (final SQLException e) {
            throw new PersistenceException("failed to get dealer in the database with id: " + id);
        }
    }

    //adds a dealer that doesn't already exist in the database
    @Override
    public Dealer addDealer(Dealer dealer)
    {
        //determine if an identical car exists in the database
        //if it does, do not add the car to the database again
        dealers = getDealerList();
        for(int i = 0; i < dealers.size(); i++)
        {
            Dealer curDel = dealers.get(i);

            if( dealer.sameDealer(curDel) )
            {
                System.out.println("dealer match found " + dealer.getName());
                return curDel;
            }

        }
        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO dealers VALUES(DEFAULT, ?, ?, ?)");

            st.setString(1, dealer.getName());
            st.setString(2, dealer.getNumber());
            st.setString(3, dealer.getEmail());

            st.executeUpdate();
            st.close();
            curIDNum++;

            return new Dealer(Integer.toString(curIDNum), dealer.getName(), dealer.getNumber(), dealer.getEmail());

        }catch (final SQLException e) {
            //should throw an exception
            throw new PersistenceException("could not add dealer to the database" + e.getMessage());
        }
    }

    //deletes a dealer from the database as well as any cars they own
    public void deleteDealer(Dealer dealer)
    {
        try (final Connection c = connection())
        {
            List<Integer> carIDs = getCarIDsByDealerID(Integer.parseInt(dealer.getId()));

            final PreparedStatement sc = c.prepareStatement("DELETE FROM carsdealers WHERE dealer_id = ?");
            final PreparedStatement st = c.prepareStatement("DELETE FROM dealers WHERE id = ?");

            st.setString(1, dealer.getId());
            sc.setString(1, dealer.getId());

            //first delete the ownership of cars for that dealer
            sc.executeUpdate();
            sc.close();

            //then delete the dealer themselves
            st.executeUpdate();
            st.close();

            //then delete every car the dealer owns from cars
            Iterator<Integer> iterator = carIDs.iterator();
            System.out.println("the cars list obtained is: " + carIDs.size());
            while(iterator.hasNext())
            {
                final PreparedStatement delCar = c.prepareStatement("DELETE FROM cars where id = ?");

                int curID = iterator.next();
                System.out.println("deleting car w id " + curID);
                delCar.setString(1, Integer.toString(curID));
                delCar.executeUpdate();
                delCar.close();
            }


        }catch (final SQLException e) {
            //should throw an exception
            throw new PersistenceException("failed to delete dealer in the database");
        }
    }


    //deletes a dealer of a specific ID as well as any car they own
    public void deleteDealerByID(String id)
    {
        try (final Connection c = connection()) {

            List<Integer> carIDs = getCarIDsByDealerID(Integer.parseInt(id));

            final PreparedStatement sc = c.prepareStatement("DELETE FROM carsdealers WHERE dealer_id = ?");
            final PreparedStatement st = c.prepareStatement("DELETE FROM dealers WHERE id = ?");
            sc.setString(1, id);
            st.setString(1, id);

            //first delete the ownership of cars for that dealer
            sc.executeUpdate();
            sc.close();

            //then delete the dealer themselves
            st.executeUpdate();
            st.close();

            //then delete every car the dealer owns from cars
            while(carIDs.size() != 0 )
            {
                final PreparedStatement delCar = c.prepareStatement("DELETE FROM cars where id = ?");
                int curID = carIDs.remove(0);
                delCar.setString(1, Integer.toString(curID));
                delCar.executeUpdate();
                delCar.close();
            }

        } catch (final SQLException e) {
            //should throw an exception
            throw new PersistenceException("failed to delete dealer in the database with id: " + id);
        }
    }

    //get only the carIDs of cars owned by this dealer in the database
    public List<Integer> getCarIDsByDealerID(int id)
    {
        try (final Connection c = connection())
        {
            final PreparedStatement cars = c.prepareStatement("SELECT * from carsdealers where dealer_id = ?");
            cars.setString(1, Integer.toString(id));
            ResultSet rs = cars.executeQuery();

            ArrayList<Integer> carIDs = new ArrayList<Integer>();

            while (rs.next()) {
                carIDs.add(rs.getInt("car_id"));
                rs.getInt("dealer_id");
            }

            rs.close();
            cars.close();

            return carIDs;
        }
        catch(final SQLException e)
        {
            throw new PersistenceException("failed to fetch caIDs owned by dealer with id: " + id);
        }
    }

    //get entire car objects owned by this dealer in the database
    public List<Car> getCarsByDealerID(int id)
    {
        try (final Connection c = connection()) {
            Dealer owner = getDealerByID(Integer.toString(id));
            List<Integer> ids = getCarIDsByDealerID(id);
            ArrayList<Car> carsList = new ArrayList<Car>();

            while (ids.size()!=0) {
                final PreparedStatement cars = c.prepareStatement("SELECT * from cars where id = ?");
                cars.setString(1, Integer.toString(ids.remove(0)));
                ResultSet rs = cars.executeQuery();

                if(rs.next())
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

                    carsList.add(new Car(carID, make, model, Integer.toString(year), String.valueOf(mileage), descrip, String.valueOf(price), trans, fuelType, owner));
                }

                rs.close();
                cars.close();
            }

            return carsList;
        }
        //if the dealer was not found, then they own no cars, so the list of cars returned is empty
        catch(final DealerNotFoundException e)
        {
            return new ArrayList<Car>();
        }
        catch(final SQLException e)
        {
            throw new PersistenceException("failed to fetch cars owned by dealer with id: " + id);
        }
    }
}
