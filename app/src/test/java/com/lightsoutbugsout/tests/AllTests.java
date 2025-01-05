package com.lightsoutbugsout.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//Import the test files
import com.lightsoutbugsout.tests.business.CarFilterManagerTest;
import com.lightsoutbugsout.tests.business.FavoriteTest;
import com.lightsoutbugsout.tests.business.FilterTest;
import com.lightsoutbugsout.tests.objects.CarsTest;
import com.lightsoutbugsout.tests.objects.DealerTest;
import com.lightsoutbugsout.tests.persistence.CarPersistanceTest;
import com.lightsoutbugsout.tests.business.CalculatorEMITest;
import com.lightsoutbugsout.tests.persistence.DealerPersistenceTest;
import com.lightsoutbugsout.tests.IntegrationTests;
import com.lightsoutbugsout.tests.business.ExceptionsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CarsTest.class,
        DealerTest.class,
        DealerPersistenceTest.class,
        CarPersistanceTest.class,
        FilterTest.class,
        FavoriteTest.class,
        CalculatorEMITest.class,
        ExceptionsTest.class,
        IntegrationTests.class,
        CarFilterManagerTest.class
})

public class AllTests{

}