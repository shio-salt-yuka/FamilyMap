package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.Fill_LoadResult;

import java.sql.Connection;

public class load_ {
    private Database db;
    private UserDAO u;
    private PersonDAO p;
    private EventDAO e;
    private AuthTokenDAO aut;
    /**
     * Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database.
     */
    public Fill_LoadResult load(LoadRequest data) throws DataAccessException {
        Fill_LoadResult result = new Fill_LoadResult();
        db = new Database();
        Connection conn = db.getConnection();
        System.out.println("Opening connection in line 29 in load service");
        u = new UserDAO(conn);
        e = new EventDAO(conn);
        p = new PersonDAO(conn);
        aut = new AuthTokenDAO(conn);

        try{
            //clear all tables
            p.clear();
            e.clear();
            u.clear();
            aut.clear();

            if (data == null){
                result.setMessage("Error: data does not exist(NULL)");
                result.setSuccess(false);
                db.closeConnection(true);
                System.out.println("Closing connection in load service");
                return result;
            }

            User[] userArray = data.getUsers();
            for (User user:userArray){
                u.insert(user);
            }

            Person[] personArray = data.getPersons();
            for (Person person:personArray){
                p.insert(person);
            }

            Event[] eventArray = data.getEvents();
            for (Event event:eventArray){
                e.insert(event);
            }

            result.setMessage("Successfully added " + userArray.length + " users, " + personArray.length + " persons, and " + eventArray.length + " events to the database.");
            result.setSuccess(true);

            db.closeConnection(true);
            System.out.println("Closing connection in load service");


        }catch(DataAccessException e){
            result.setMessage("Error: unable to load data");
            result.setSuccess(false);
            db.closeConnection(true);
            System.out.println("Closing connection in load service");

        }
        return result;
    }
}
