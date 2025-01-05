package application;

import persistence.CarPersistence;
import persistence.hsqldb.CarPersistenceHSQLDB;
import persistence.DealerPersistence;
import persistence.hsqldb.DealerPersistenceHSQLDB;
import persistence.stubs.CarPersistenceStub;
import persistence.stubs.DealerPersistenceStub;

public class Services
{

    //change to false to fetch data from stub
    //Before switching formats we recommend cleaning the storage and cache data for the app on the emulator
    private static final boolean fromDB = true;
    private static CarPersistence carPersistence = null;
    private static DealerPersistence dealerPersistence = null;
    public static synchronized CarPersistence getCarPersistence()
    {
        if(carPersistence == null ) {
            if (fromDB) {
                carPersistence = new CarPersistenceHSQLDB(Main.getDBPathName());
            } else {
                carPersistence = new CarPersistenceStub();
            }
        }

        return carPersistence;
    }

    public static synchronized DealerPersistence getDealerPersistence()
    {
        if(dealerPersistence == null) {
            if (fromDB) {
                dealerPersistence = new DealerPersistenceHSQLDB(Main.getDBPathName());
            } else {
                dealerPersistence = new DealerPersistenceStub();
            }
        }
        return dealerPersistence;
    }

}
