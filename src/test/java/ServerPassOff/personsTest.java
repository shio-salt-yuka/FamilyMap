package ServerPassOff;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import Request.LoadRequest;
import Result.peopleResult;
import Service.load_;
import Service.person_;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class personsTest {
    Gson GSON = new Gson();
    JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
    LoadRequest loadRequestTestData = GSON.fromJson(jsonReader, LoadRequest.class);
    private final AuthToken goodData = new AuthToken("sheila", "goodAuthToken");
    private final AuthToken badData = new AuthToken("bad username", "bad AuthToken");

    private Database db;
    person_ service = new person_();
    load_ loadservice = new load_();

    public personsTest() throws FileNotFoundException {
    }

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        loadservice.load(loadRequestTestData);
        db.closeConnection(true);
    }

    @Test
    public void personsGoodTest() throws DataAccessException {
        peopleResult result = service.person(goodData);
        assertNotNull(result.getMessage());
        assertTrue(result.isSuccess());
    }

    @Test
    public void personsBadTest() throws DataAccessException {
        peopleResult result = service.person(badData);
        assertFalse(result.isSuccess());
    }
}
