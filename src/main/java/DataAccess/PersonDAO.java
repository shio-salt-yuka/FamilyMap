package DataAccess;

import Model.Person;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDAO {
    private final Connection conn;
    Gson gson = new Gson ();

    public PersonDAO(Connection conn) {
        this.conn = conn;
        System.out.println("Opening connection in line 20 in PersonDAO");

    }
    /**
     * adding new Person with information provided by user
     * @param person object
     */
    public void insert(Person person) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO person (personID, a_Username, f_name, l_name, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getA_Username());
            stmt.setString(3, person.getF_name());
            stmt.setString(4, person.getL_name());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
    /**
     * finding person's information via personID provided by user
     * @param personID
     * @return Person object, the person info in search
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("a_Username"),
                        rs.getString("f_name"), rs.getString("l_name"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
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

    public ArrayList getAll(String associatedUsername) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM person WHERE a_Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            ArrayList <Person> people = new ArrayList();
            while(!rs.isAfterLast()){
                if (rs.next()) {
                    person = new Person(rs.getString("personID"), rs.getString("a_Username"),
                            rs.getString("f_name"), rs.getString("l_name"), rs.getString("gender"),
                            rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                    people.add(person);
                }
            }
            return people;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * clear/remove all person information.
     */
    public void clear() throws DataAccessException{
        String sql = "DELETE FROM person";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    public void clearByusername(String username) throws DataAccessException{
        String sql = "DELETE FROM person WHERE a_Username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
            //System.out.println("Person table all cleared");
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

}
