package DataAccess;

import Model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthTokenDAO {
    private final Connection conn;

    public AuthTokenDAO(Connection conn)
    {
        this.conn = conn;
        System.out.println("Opening connection in line 15 in AuthTokenDAO");
    }

    /**
     * adding/creating new authorization token with information provided by user
     * @param authtoken object
     */
    public void insert(AuthToken authtoken) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO authtoken (username, authToken) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, authtoken.getUsername());
            stmt.setString(2, authtoken.getAuthToken());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
    /**
     * finding authtoken information via username provided by user
     * @param username
     * @return Authtoken object, the authtoken info in search
     */
    public AuthToken find(String username) throws DataAccessException {
        AuthToken authtoken;
        ResultSet rs = null;
        String sql = "SELECT * FROM authtoken WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authtoken = new AuthToken(rs.getString("username"), rs.getString("authtoken"));
                return authtoken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding authtoken");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public AuthToken findAuthToken(String userToken) throws DataAccessException {
        AuthToken authtoken;
        ResultSet rs = null;
        String sql = "SELECT * FROM authtoken WHERE authToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authtoken = new AuthToken(rs.getString("username"), rs.getString("authtoken"));
                return authtoken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding authtoken");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
    /**
     * clear/remove all authtoken information.
     */
    public void clear() throws DataAccessException{
        String sql = "DELETE FROM authtoken";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
}
