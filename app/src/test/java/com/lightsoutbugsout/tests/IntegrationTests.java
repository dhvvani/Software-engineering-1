package com.lightsoutbugsout.tests;

import com.lightsoutbugsout.tests.business.CarHandlerIT;
import com.lightsoutbugsout.tests.business.DealerHandleIT;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CarHandlerIT.class,
        DealerHandleIT.class
})
public class IntegrationTests {
}
