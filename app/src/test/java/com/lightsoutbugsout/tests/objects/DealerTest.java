package com.lightsoutbugsout.tests.objects;

import static org.junit.Assert.*;

import org.junit.Test;

import objects.Dealer;
public class DealerTest {
    @Test
    public void testDealer() {

        System.out.println("Starting Dealer Object Test");

        //Dealer
        Dealer[] dealers = new Dealer[3];
        dealers[0] = new Dealer("121","Heisenberg","9999999999","xyz@abc.com");
        dealers[1] = new Dealer("173", "Joker","8888888888", "abc@xyz.com" );
        dealers[2] = new Dealer("121","Heisenberg","9999999999","xyz@abc.com");

        assertNotNull(dealers[0]);
        assertNotEquals(dealers[0].getId(), dealers[1].getId());
        assertTrue(dealers[0].equals(dealers[2]));
        assertEquals("121", dealers[0].getId());
        assertEquals("Heisenberg", dealers[0].getName());
        assertEquals("9999999999", dealers[0].getNumber());
        assertEquals("xyz@abc.com", dealers[0].getEmail());

        System.out.println("Finished Dealer Object Test\n");
    }
}
