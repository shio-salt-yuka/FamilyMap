package ServerPassOff;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.RegisterRequest;
import Result.Fill_LoadResult;
import Service.fill_username;
import Service.user_register;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class fillTest {

    private final RegisterRequest testUser = new RegisterRequest("yuka","yuka123","shiobara@email.com",  "Yuka", "Shiobara", "f");


    private Database db;


    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        user_register service = new user_register();
        service.register(testUser);
        db.closeConnection(true);
    }

    @Test
    public void fillGoodTest() throws DataAccessException {
        fill_username service = new fill_username();
        Fill_LoadResult result = service.fill(testUser.getUsername(), 4);
        assertNotNull(result.getMessage());
        assertTrue(result.isSuccess());
    }

    @Test
    public void fillBadTest() throws DataAccessException {
        fill_username service = new fill_username();
        Fill_LoadResult result = service.fill("bad username",3);
        assertFalse(result.isSuccess());
    }
}
