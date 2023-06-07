package Request;

public class personRequest {
    private String personID;
    private String authToken;

    public personRequest(String personID) {
        this.personID = personID;
    }

    public personRequest(String personID, String authToken) {
        this.personID = personID;
        this.authToken = authToken;
    }

    //public personRequest() {}

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
