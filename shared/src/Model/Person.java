package Model;

import java.util.Objects;

public class Person {
    private String personID;
    private String associatedUsername;
    private String firstName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personID.equals(person.personID) && associatedUsername.equals(person.associatedUsername) && firstName.equals(person.firstName) && lastName.equals(person.lastName) && gender.equals(person.gender) && Objects.equals(fatherID, person.fatherID) && Objects.equals(motherID, person.motherID) && Objects.equals(spouseID, person.spouseID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
    }

    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    public Person(String personID, String a_Username, String f_name, String l_name,
                  String gender, String fatherID, String motherID, String spouseID){
        this.personID = personID;
        this.associatedUsername = a_Username;
        this.firstName = f_name;
        this.lastName = l_name;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

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

    public String getL_name() {
        return lastName;
    }

    public void setL_name(String l_name) {
        this.lastName = l_name;
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

    /**
     * comparing Person objects
     * @param object
     */
//    public boolean equals(Object o) {
//        if (o == null)
//            return false;
//        if (o instanceof Person) {
//            Person oPerson = (Person) o;
//            return oPerson.getPersonID().equals(getPersonID()) &&
//                    oPerson.getA_Username().equals(getA_Username()) &&
//                    oPerson.getF_name().equals(getF_name()) &&
//                    oPerson.getL_name().equals(getL_name()) &&
//                    oPerson.getGender().equals(getGender()) &&
//                    oPerson.getFatherID().equals(getFatherID()) &&
//                    oPerson.getMotherID().equals(getMotherID()) &&
//                    oPerson.getSpouseID().equals(getSpouseID());
//        } else {
//            return false;
//        }
//    }
}
