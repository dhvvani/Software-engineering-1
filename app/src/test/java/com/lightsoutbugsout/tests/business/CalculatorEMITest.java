package com.lightsoutbugsout.tests.business;


import static junit.framework.TestCase.assertEquals;
import org.junit.Before;
import org.junit.Test;
import business.IcalculatorEMI;

public class CalculatorEMITest {
    @Before
    public void setup(){
        System.out.println("Starting test for Monethly Payment Tool logic");
    }

    @Test
    public void testCalculateEMI(){
        System.out.println(" * Started Test to calculate monthly installment");
        double price=20000;
        double downP=10000;
        double interest=10;
        double loanT=12;
        double result=IcalculatorEMI.calculateEMI(price,downP,interest,loanT);
        double expected= (double) 11000 /12;
        assertEquals(result,expected,0.0001);
        System.out.println(" * Finished Test to calculate monthly installment");

    }

}
