package ServerPassOff;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.LoadRequest;
import Result.Fill_LoadResult;
import Service.load_;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class loadTest {
    Gson GSON = new Gson();
    JsonReader jsonReader = new JsonReader(new FileReader("passoffFiles/LoadData.json"));
    LoadRequest loadRequestTestData = GSON.fromJson(jsonReader, LoadRequest.class);
    private final LoadRequest badData = null;


    private Database db;

    public loadTest() throws FileNotFoundException {
    }


    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void loadGoodTest() throws DataAccessException {
        load_ service = new load_();
        Fill_LoadResult result = service.load(loadRequestTestData);
        assertNotNull(result.getMessage());
        assertTrue(result.isSuccess());
    }

    @Test
    public void loadBadTest() throws DataAccessException {
        load_ service = new load_();
        Fill_LoadResult result = service.load(badData);
        assertFalse(result.isSuccess());
    }
}
