package Result;

/**
 *  Returns the single Person object with the specified ID.
 */
public class PersonIDResult {
    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID = null;
    private String motherID = null;
    private String spouseID = null;
    private boolean success;
    private String message;

    public PersonIDResult(String personID, String a_Username, String f_name, String l_name, String gender,
                          String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = a_Username;
        this.firstName = f_name;
        this.lastName = l_name;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;

    }

    public PersonIDResult(){}

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getA_Username() {
        return associatedUsername;
    }

    public void setA_Username(String a_Username) {
        this.associatedUsername = a_Username;
    }

    public String getF_name() {
        return firstName;
    }

    public void setF_name(String f_name) {
        this.firstName = f_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
