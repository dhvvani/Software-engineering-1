package business.exceptions;

public class CarNotFoundException extends RuntimeException{

    //Throws a CarNotFoundException to the superclass (RuntimeException)
    public CarNotFoundException(String error){
        super("The Car was not found\n" + error);
    }
}
