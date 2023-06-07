package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.User;
import Request.RegisterRequest;
import Result.Register_LoginResult;

import java.sql.Connection;
import java.util.UUID;

public class user_register {
    private Database db;
    private UserDAO u;
    private PersonDAO p;
    private AuthTokenDAO aut;

    /**
     * Creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in, and returns an auth token.
     */
    public Register_LoginResult register(RegisterRequest newUserInfo ) throws DataAccessException {
        Register_LoginResult result = new Register_LoginResult();
        UUID personID = UUID.randomUUID(); //Generates random personID
        UUID authToken = UUID.randomUUID();
        db = new Database();
        Connection conn = db.getConnection();
        System.out.println("Opening connection in line 30 in register service");
        u = new UserDAO(conn);
        aut = new AuthTokenDAO(conn);
        p = new PersonDAO(conn);

        try{
            User user = u.find(newUserInfo.getUsername());

            if(user == null){//new user only

                user = new User(newUserInfo.getUsername(),newUserInfo.getPassword(),newUserInfo.getEmail(),
                        newUserInfo.getFirstName(), newUserInfo.getLastName(),newUserInfo.getGender(),personID.toString());
                AuthToken token = new AuthToken(user.getUsername(), authToken.toString());
                aut.insert(token);

                u.insert(user);
                db.closeConnection(true);
                System.out.println("Closing connection in register service");


                fill_username generateFamily = new fill_username();
                generateFamily.fill(newUserInfo.getUsername(),4);

                db.getConnection();
                result = new Register_LoginResult(authToken.toString(),user.getUsername(), user.getPersonID());
                result.setSuccess(true);
                db.closeConnection(true);
                System.out.println("Closing connection in register service");


                return result;

            }else if(user != null){
                result.setMessage("Error: user already exists");
                result.setSuccess(false);
                db.closeConnection(true);
                System.out.println("Closing connection in register service");


            }
        }catch(DataAccessException e){
            result.setMessage("Error: unable to register data");
            result.setSuccess(false);
            db.closeConnection(true);
            System.out.println("Closing connection in register service");


        }

        return result;
    }
}
