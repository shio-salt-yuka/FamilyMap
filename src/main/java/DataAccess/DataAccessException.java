package DataAccess;

public class DataAccessException extends Exception {
    /**
     * Exception error
     * @param
     */
    DataAccessException(String message)
    {
        super(message);
    }

    DataAccessException()
    {
        super();
    }
}
