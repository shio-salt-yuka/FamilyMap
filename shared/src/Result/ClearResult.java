package Result;
/**
 *  Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data.
 */
public class ClearResult {

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
