package Result;

import Model.Person;

import java.util.ArrayList;

/**
 *  Returns all Person object associated with user input.
 */
public class peopleResult {
    private ArrayList<Person> data;
    private boolean success;
    private String message = "personResult success";

    public peopleResult(ArrayList<Person> data) {
        this.data = data;
    }

    public peopleResult() {}

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
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
