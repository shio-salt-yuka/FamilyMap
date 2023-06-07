package Model;

public class randomData {
    String female;
    String surname;
    String male;
    location locationInfo;


    public randomData(String female, String surname, String male, location locationInfo) {
        this.female = female;
        this.surname = surname;
        this.male = male;
        this.locationInfo = locationInfo;
    }

    public String getFemale() {
        return female;
    }

    public void setFemale(String female) {
        this.female = female;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public location getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(location locationInfo) {
        this.locationInfo = locationInfo;
    }
}
