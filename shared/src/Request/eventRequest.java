package Request;

import Model.AuthToken;

public class eventRequest {
    private String eventID;
    private String authToken = "";
    private AuthToken token;

    public eventRequest(String eventID, String authToken) {
        this.eventID = eventID;
        this.authToken = authToken;
    }

    public eventRequest(String eventID, AuthToken token) {
        this.eventID = eventID;
        this.token = token;
        authToken = token.getAuthToken();
    }
    public eventRequest(String eventID) {
        this.eventID = eventID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }
}
