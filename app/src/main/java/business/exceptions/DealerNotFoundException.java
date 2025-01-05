package business.exceptions;

public class DealerNotFoundException extends RuntimeException{
        //Throws a DealerNotFound Exception to the superclass (RuntimeException)
        public DealerNotFoundException(String error){
            super("The Dealer was not found\n" + error);
        }
    }

