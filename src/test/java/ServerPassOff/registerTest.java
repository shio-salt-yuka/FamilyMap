package ServerPassOff;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.RegisterRequest;
import Result.Register_LoginResult;
import Service.user_register;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class registerTest {

    private final RegisterRequest goodRequest = new RegisterRequest("shiobara","yuka123","shiobara@email.com",  "Yuka", "Shiobara", "f");
    private final RegisterRequest badRequest = new RegisterRequest(null ,"badInfo","badInfo",  "badInfo", "badInfo", "f");
    private Database db;

    @BeforeEach
    public void clearTest() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    public void registerGoodTest() throws DataAccessException {
        user_register service = new user_register();
        Register_LoginResult result = service.register(goodRequest);
        assertNotNull(result.getAuthToken());
        assertNotNull(result.getPersonID());
        assertTrue(result.isSuccess());
    }

    @Test
    public void registerBadTest() throws DataAccessException {
        user_register service = new user_register();
        Register_LoginResult result = service.register(badRequest);
        assertNull(result.getAuthToken());
        assertFalse(result.isSuccess());
    }
}
