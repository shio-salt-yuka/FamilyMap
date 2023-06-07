package Model;

public class AuthToken {
    private String username;
    private String authToken;

    /**
     * AuthToken constructor
     * @param username given by the user
     * @param  authToken
     */
    public AuthToken(String username, String authToken){
        this.username = username;
        this.authToken = authToken; // generated unique authToken
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * comparing AuthToken objects
     * @param o
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof AuthToken) {
            AuthToken oAuthToken = (AuthToken) o;
            return oAuthToken.getUsername().equals(getUsername()) &&
                    oAuthToken.getAuthToken().equals(getAuthToken());

        } else {
            return false;
        }
    }
}
