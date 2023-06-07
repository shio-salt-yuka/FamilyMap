package Service;

import DataAccess.*;
import Result.ClearResult;

import java.sql.Connection;


public class clear_ {
    private Database db;
    private PersonDAO pDao;
    private EventDAO eDao;
    private UserDAO uDao;
    private AuthTokenDAO aDao;

    /**
     *  Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data.
     * @return
     */
    public ClearResult clearAll() throws DataAccessException {
        ClearResult result = new ClearResult();
        db = new Database();
        Connection conn = db.getConnection();
        System.out.println("Opening connection in line 26 in clear service");
        pDao = new PersonDAO(conn);
        eDao = new EventDAO(conn);
        uDao = new UserDAO(conn);
        aDao = new AuthTokenDAO(conn);
        try{
            pDao.clear();
            eDao.clear();
            uDao.clear();
            aDao.clear();

            result.setMessage("clear succeeded.");
            result.setSuccess(true);

            db.closeConnection(true);
            System.out.println("Closing connection in clear service");


        }catch(DataAccessException e){
            result.setMessage("Error: could not clear data");
            result.setSuccess(false);
            db.closeConnection(true);
            System.out.println("Closing connection in clear service");

        }
        return result;
    }

}
