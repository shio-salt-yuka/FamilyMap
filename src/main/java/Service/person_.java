package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.AuthToken;
import Model.Person;
import Result.peopleResult;

import java.sql.Connection;
import java.util.ArrayList;

public class person_ {
    private Database db;
    private PersonDAO p;
    /**
     * Returns ALL family members of the current user. The current user is determined from the provided auth token
     */
    public peopleResult person(AuthToken token)throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        System.out.println("Opening connection in line 23 in person service");
        p = new PersonDAO(conn);
        peopleResult response;

        try{

            ArrayList<Person> array = p.getAll(token.getUsername());
            if(array.size() == 0){
                response = new peopleResult();
                response.setMessage("Error: username does not exist");
                response.setSuccess(false);

                db.closeConnection(true);
                return response;
            }

            response = new peopleResult(p.getAll(token.getUsername()));

            response.setSuccess(true);

            db.closeConnection(true);
        } catch(DataAccessException e){
            response = new peopleResult();
            response.setMessage("Error: unable to retrieve data");
            response.setSuccess(false);
            db.closeConnection(true);
        }

        return response;
    }
}
