package business.exceptions;

public class DuplicationException extends RuntimeException
{
    //Throws a duplicate instance exception
    public DuplicationException(String error) {
        super("Duplication exception: \n" + error);
    }
}
