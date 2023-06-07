package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.Event;
import Request.eventRequest;
import Result.EventIDResult;

import java.sql.Connection;

public class event_eventid {
    private Database db;
    private EventDAO e;
    /**
     * Returns the single Event object with the specified ID.
     */
    public EventIDResult eventID(eventRequest eventID) throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        System.out.println("Opening connection in line 18 in eventid service");
        e = new EventDAO(conn);
        EventIDResult response;

        try{
            Event r = e.find(eventID.getEventID());
            if (r == null){
                response = new EventIDResult();
                response.setMessage("Error: eventID does not exist");
                response.setSuccess(false);
                db.closeConnection(true);
                return response;
            }


            response = new EventIDResult(r.getEventID(), r.getUsername(), r.getPersonID(), r.getLatitude(), r.getLongitude(),
                    r.getCountry(), r.getCity(), r.getEventType(), r.getYear());

            //response.setMessage("Event found");
            response.setSuccess(true);

            db.closeConnection(true);

        } catch(DataAccessException e){
            response = new EventIDResult();
            response.setMessage("Error: unable to retrieve data");
            response.setSuccess(false);
            db.closeConnection(true);
        }

        return response;
    }
}
