package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.Register_LoginResult;

import java.sql.Connection;
import java.util.UUID;

public class user_login {
    private Database db;
    private UserDAO u;
    private AuthTokenDAO aut;

    /**
     * Logs in the user and returns an auth token
     */
    public Register_LoginResult login(LoginRequest newUserInfo) throws DataAccessException {
        Register_LoginResult result = new Register_LoginResult();
        db = new Database();
        Connection conn = db.getConnection();
        System.out.println("Opening connection in line 25 in login service");
        u = new UserDAO(conn);
        aut = new AuthTokenDAO(conn);
        String username;
        String password;
        AuthToken token;
        UUID authToken = UUID.randomUUID();
        try{

            User user = u.find(newUserInfo.getUsername());

            if(user != null && newUserInfo.getPassword().equals(user.getPassword())) { //finding user info

                username = user.getUsername();
                password = user.getPassword();
                token = new AuthToken(user.getUsername(), authToken.toString());
                aut.insert(token);
                token = aut.find(username);

                result = new Register_LoginResult(token.getAuthToken(), username, user.getPersonID());
                result.setMessage("Logged in!");
                result.setSuccess(true);

            }else if(user == null){
                result.setMessage("Error: username not found");
                result.setSuccess(false);
            }else{
                result.setMessage("Error: password not found");
                result.setSuccess(false);
            }

            db.closeConnection(true);
            System.out.println("Closing connection in login service");


        }catch(DataAccessException e){
            result.setMessage("Error: unable to retrieve data");
            result.setSuccess(false);
            db.closeConnection(true);
            System.out.println("Closing connection in login service");

        }

//        db.closeConnection(true);
//        System.out.println("Closing connection in login service before return");
        return result;
    }

}
