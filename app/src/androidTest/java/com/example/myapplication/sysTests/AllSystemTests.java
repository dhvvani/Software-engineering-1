package com.example.myapplication.sysTests;

import com.example.myapplication.AddCarTest;
import com.example.myapplication.AddDealerTest;
import com.example.myapplication.CalculatorEMIToolTest;
import com.example.myapplication.ExampleInstrumentedTest;
import com.example.myapplication.FavoriteSystemTest;
import com.example.myapplication.FilterSystemTest;
import com.example.myapplication.TestBuyCar;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.logging.FileHandler;

import presentation.MainActivity;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddCarTest.class,
        AddDealerTest.class,
        CalculatorEMIToolTest.class,
        ExampleInstrumentedTest.class,
        FavoriteSystemTest.class,
        FilterSystemTest.class,
        TestBuyCar.class
})


public class AllSystemTests
{
    @BeforeClass
    public static void clearAppData() {
        MainActivity.shouldClearData = true;
    }
}
