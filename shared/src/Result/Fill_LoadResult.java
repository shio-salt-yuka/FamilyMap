package Result;
/**
 *  Fill: Populates the server's database with generated data for the specified user name.
 *  The required "username" parameter must be a user already registered with the server.
 *  If there is any data in the database already associated with the given user name, it is deleted.
 *  The optional “generations”parameter lets the caller specify the number of generations of ancestors to be generated
 *
 *  Load: Clears all data from the database (just like the /clear API), and then loads the posted user, person,
 *  and event data into the database.
 */
public class Fill_LoadResult {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
