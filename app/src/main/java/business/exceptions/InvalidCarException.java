package business.exceptions;

public class InvalidCarException extends RuntimeException{
    //Throws a Invalid Car Exception to the superclass (RuntimeException)
    public InvalidCarException(String error){
        super("Invalid Car\n" + error);
    }
}