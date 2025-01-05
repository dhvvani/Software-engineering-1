package business.exceptions;

public class InvalidDealerException extends RuntimeException{
    //Throws an InvalidDealer Exception to the superclass (RuntimeException)
    public InvalidDealerException(String error){
        super("Invalid Dealer\n" + error);
    }
}
