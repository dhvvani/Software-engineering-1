package business.exceptions;

public class PersistenceException extends RuntimeException
{
    //Throws a duplicate instance exception
    public PersistenceException(String error) {
        super("Persistence exception: \n" + error);
    }
}
