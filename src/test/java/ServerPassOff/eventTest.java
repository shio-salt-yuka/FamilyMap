package ServerPassOff;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.AuthToken;
import Model.Event;
import Request.eventRequest;
import Result.EventIDResult;
import Service.event_eventid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class eventTest {
    private final double latitudeTest = 32.6667;
    private final double longitudeTest = -114.5333;
    private final Event testEvent = new Event("testEventID", "yuka", "testPersonID", (float)latitudeTest, (float)longitudeTest,
    "Mexico", "Mexico city", "Vacation", 2000);
    private final AuthToken testToken = new AuthToken("yuka", "authToken123");
    private final eventRequest goodRequest = new eventRequest("testEventID", testToken);
    private final eventRequest badRequest = new eventRequest("badEventID", "bad token");
    private Database db;
    private EventDAO eDao;
    private AuthTokenDAO aDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        eDao = new EventDAO(conn);
        aDao = new AuthTokenDAO(conn);
        db.clearTables();
        aDao.insert(testToken);
        eDao.insert(testEvent);
        db.closeConnection(true);
    }

    @Test
    public void eventIDGoodTest() throws DataAccessException {
        event_eventid service = new event_eventid();
        EventIDResult result = service.eventID(goodRequest);
        assertNotNull(result.getEventID());
        assertNotNull(goodRequest.getAuthToken());
        assertTrue(result.isSuccess());
    }

    @Test
    public void eventIDBadTest() throws DataAccessException {
        event_eventid service = new event_eventid();
        EventIDResult result = service.eventID(badRequest);
        assertFalse(result.isSuccess());
    }
}
