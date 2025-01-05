package objects;

import androidx.annotation.NonNull;

public class Dealer
{
    //Required variables for a Dealer IOT store in the database
    private final String id;
    private final String name;
    private final String number;
    private final String email;

    // Constructor
    public Dealer(String id, String name, String number, String email)
    {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
    }// Constructor

    public String getName(){ return this.name; }        //return the name of the dealer
    public String getNumber(){ return this.number; }    //return the phone number of the dealer
    public String getEmail(){ return this.email; }      //return the email of the dealer
    public String getId(){ return this.id; }            //return the ID of the dealer
    public int getNumericID(){ return Integer.parseInt(id);}    //return the ID as an integer

    @NonNull
    public String toString(){
        return id+"\n"+name + "\n" + number + "\n" + email + "\n";
    }

    //Determine whether this.dealer is the same as newDealer by ID
    public boolean equals(Dealer newDealer){
        return this.id.equals(newDealer.getId());
    }

    //this method is required other than the .equals() method as dealers are inserted using the HSQL database's DEFAULT id
    //the new dealer's id may be different but if the name and all other properties are the same, then the two are the same.
    public boolean sameDealer(Dealer newDel)
    {
        return this.name.equalsIgnoreCase(newDel.getName()) &&
               this.number.equalsIgnoreCase(newDel.getNumber()) &&
               this.email.equalsIgnoreCase(newDel.getEmail());
    }

}//Dealer