package ServerPassOff;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import Request.LoadRequest;
import Result.ClearResult;
import Service.clear_;
import Service.load_;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class clearTest {
    private Database db;
    private AuthTokenDAO aut;
    Gson GSON = new Gson();
    JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
    LoadRequest loadRequestTestData = GSON.fromJson(jsonReader, LoadRequest.class);
    private final AuthToken testToken = new AuthToken("sheila", "goodTestToken");


    public clearTest() throws FileNotFoundException {
    }

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        aut = new AuthTokenDAO(conn);
        aut.insert(testToken);
        db.closeConnection(true);

        load_ service = new load_();
        service.load(loadRequestTestData);
    }

    @Test
    public void clearGoodTest() throws DataAccessException {
        clear_ service = new clear_();
        ClearResult result = service.clearAll();
        assertNotNull(result.getMessage());
        assertTrue(result.isSuccess());
    }

    @Test
    public void clearBadTest() throws DataAccessException {
        clear_ service = new clear_();
        service.clearAll();
        ClearResult result2 = service.clearAll();
        assertTrue(result2.isSuccess());
    }
}
