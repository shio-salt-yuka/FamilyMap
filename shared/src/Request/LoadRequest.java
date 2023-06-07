package Request;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database.
 */
public class LoadRequest { //Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database.

    private User[] users;
    private Person[] persons;
    private Event[] events;

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }


    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
