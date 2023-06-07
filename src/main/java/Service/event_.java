package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.AuthToken;
import Model.Event;
import Result.eventResult;

import java.sql.Connection;
import java.util.ArrayList;

public class event_ {
    private Database db;
    private EventDAO e;
    /**
     * Returns ALL family members of the current user. The current user is determined from the provided auth token
     */
    public eventResult event(AuthToken token)throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        System.out.println("Opening connection in line 20 in event service");
        e = new EventDAO(conn);
        eventResult response;

        try{

// arraylist to array
            ArrayList<Event> array = e.getAll(token.getUsername());
            if(array.size() == 0){
                response = new eventResult();
                response.setMessage("Error: username does not exist");
                response.setSuccess(false);

                db.closeConnection(true);
                return response;
            }

            response = new eventResult(array.toArray(new Event[0]));

            response.setSuccess(true);

            db.closeConnection(true);
        } catch(DataAccessException e){
            response = new eventResult();
            response.setMessage("Error: unable to retrieve data");
            response.setSuccess(false);

            db.closeConnection(true);
        }

        return response;
    }
}


