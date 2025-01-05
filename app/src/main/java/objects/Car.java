package objects;

public class Car {
    private final int id;//car id
    private final String make; //make of the car
    private final String model; // model of the car
    private final String year; //the year of the car
    private final String Km; //the KM driven
    //private List<String>des; //the description of the car;

    private final String des;
    private final String price;//the price of the car
    private final String trans; //the transmission of the car. If it is auto return true. othewise false.
    private final String fuel; //the type of fuel. If it is gasoline true. Otherwise false.

    private final Dealer owner;

    //will consist of a unique set of characters which define the car after taking into consideration feedback from iteration2
    private final String transactionID;


    // Constructor
    public Car(int carId, String rMake, String rModel,  String rYear,
               String rKm, String rDescription, String rPrice, String rTrans,
               String rFuel, Dealer owner)
    {
        //initialize the car
        id=carId;
        make=rMake;
        model=rModel;
        year=rYear;
        Km=rKm;
        des=rDescription;

        price=rPrice;
        trans=rTrans;
        fuel=rFuel;
        this.owner = owner;

        /* each car created will have a unique transaction string - if the owner, make, model, year,
        transmission and fuel_type are the same then the two cars are equal. We considered a shorter list of attributes,
        however, if one was excluded, say transmission, and two cars had everything identical except transmission,
        it is not fair to say that the two cars are the same.*/

        transactionID = owner.getName()+rMake+rModel+rYear+rTrans+rFuel;
    }// Constructor

    public int getId(){return id;}// accessor id

    public String getMake(){return make;}// accessor make

    public String getModel(){return model;}// accessor model

    public String getYear(){return year;}// accessor year

    public String getKm(){return Km;}// accessor km

    public String getDes(){return des;}// accessor description

    public String getPrice(){return price;}// accessor price

    public String getTrans(){return trans;}// accessor transmissions

    public String getFuel(){return fuel;}// accessor fuel

    public String toString() {
        return des;
    }

    public Dealer getOwner(){return owner;}    //get the Owner

    public String getTransactionID(){ return transactionID; }//accessor transactionID


    //equals - determine whether this.car and newCar are duplicates
    public boolean equals(Car newCar){
        boolean flag= this.getId()==(newCar.getId()) && this.getMake().equalsIgnoreCase(newCar.getMake());
        return  flag;
    }//equals

    //this method is required other than the .equals() method as cars are inserted using the HSQL database's DEFAULT id
    //the new car's id may be different but if the transactionIDs are the same, then the two are the same
    public boolean sameTransactionIDs(Car car)
    {
        return this.transactionID.equalsIgnoreCase(car.getTransactionID());
    }

}//Car