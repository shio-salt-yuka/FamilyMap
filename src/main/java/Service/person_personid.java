package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.Person;
import Request.personRequest;
import Result.PersonIDResult;

import java.sql.Connection;

public class person_personid {
    private Database db;
    private PersonDAO p;

    /**
     * Returns the single Person object with the specified ID.
     */
    public PersonIDResult personID(personRequest personID) throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        System.out.println("Opening connection in line 19 in personid service");
        p = new PersonDAO(conn);
        PersonIDResult response = new PersonIDResult();

        try{
            Person r = p.find(personID.getPersonID());
            if (r == null){
                response = new PersonIDResult();
                response.setMessage("Error: personID does not exist");
                response.setSuccess(false);
                db.closeConnection(true);
                return response;
            }

            response = new PersonIDResult(r.getPersonID(), r.getA_Username(), r.getF_name(), r.getL_name(), r.getGender(),
                    r.getFatherID(),r.getMotherID(),r.getSpouseID());
            response.setMessage("Person found");
            response.setSuccess(true);
            db.closeConnection(true);
        } catch(DataAccessException e){
            //response = new PersonID();
            response.setMessage("Error: unable to retrieve data");
            response.setSuccess(false);
            db.closeConnection(true);
        }

        return response;
    }
}

