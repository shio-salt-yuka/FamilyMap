package Result;

import Model.Event;

/**
 *  Returns the all Event object.
 */
public class eventResult {
    private Event[] data;
    private boolean success;
    private String message = "eventResult success";

    public eventResult(Event[] data) {
        this.data = data;
    }

    public eventResult() {}

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
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

