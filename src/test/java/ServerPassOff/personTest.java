package ServerPassOff;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.AuthToken;
import Model.Person;
import Request.personRequest;
import Result.PersonIDResult;
import Service.person_personid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class personTest {
    private final Person testPerson = new Person("testPersonID", "yuka", "Yuka", "Shiobara",
            "f", "testFatherID", "testMotherID", "testSpouseID");
    private final AuthToken testToken = new AuthToken("yuka", "authToken123");
    private final personRequest goodRequest = new personRequest("testPersonID", "authToken123");
    private final personRequest badRequest = new personRequest("badPersonID", "bad token");
    private Database db;
    private PersonDAO pDao;
    private AuthTokenDAO aDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        pDao = new PersonDAO(conn);
        aDao = new AuthTokenDAO(conn);
        db.clearTables();
        pDao.insert(testPerson);
        aDao.insert(testToken);
        db.closeConnection(true);
    }

    @Test
    public void personIDGoodTest() throws DataAccessException {
        person_personid service = new person_personid();
        PersonIDResult result = service.personID(goodRequest);
        assertNotNull(result.getPersonID());
        assertNotNull(goodRequest.getAuthToken());
        assertTrue(result.isSuccess());
    }

    @Test
    public void personIDBadTest() throws DataAccessException {
        person_personid service = new person_personid();
        PersonIDResult result = service.personID(badRequest);
        assertFalse(result.isSuccess());
    }
}
