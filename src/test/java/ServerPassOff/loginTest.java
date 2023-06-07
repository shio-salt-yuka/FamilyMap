package ServerPassOff;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDAO;
import Model.User;
import Request.LoginRequest;
import Result.Register_LoginResult;
import Service.user_login;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class loginTest {
    private final User testUser = new User("yuka", "yuka123", "yuka@email.com", "Yuka", "Shiobara", " f", "testPersonID");
    private final LoginRequest goodRequest = new LoginRequest("yuka","yuka123");
    private final LoginRequest badRequest = new LoginRequest("bad username", "bad password");

    private Database db;
    private UserDAO uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        uDao = new UserDAO(conn);
        db.clearTables();
        uDao.insert(testUser);
        db.closeConnection(true);
    }

    @Test
    public void loginGoodTest() throws DataAccessException {
        user_login service = new user_login();
        Register_LoginResult result = service.login(goodRequest);
        assertNotNull(result.getAuthToken());
        assertNotNull(result.getUsername());
        assertTrue(result.isSuccess());
    }

    @Test
    public void loginBadTest() throws DataAccessException {
        user_login service = new user_login();
        Register_LoginResult result = service.login(badRequest);
        assertFalse(result.isSuccess());
    }
}
