package Result;
/**
 *  Register: Creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in, and returns an auth token.
 *
 *  Login: Logs in the user and returns an auth token.
 */
public class Register_LoginResult {
    private String authtoken;
    private String username;
    private String personID;
    private String message;
    private boolean success;


    public Register_LoginResult() {}

    public Register_LoginResult(String authToken, String username, String personId) {
        this.authtoken = authToken;
        this.username = username;
        this.personID = personId;
    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authToken) {
        this.authtoken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
