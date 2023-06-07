package Service;

import DataAccess.*;
import Model.*;
import Result.Fill_LoadResult;
import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class fill_username {
    private Database db;
    private PersonDAO pDao;
    private EventDAO eDao;
    private UserDAO uDao;
    private AuthTokenDAO aDao;
    private Integer personNum = 0;
    private Integer eventNum = 0;
    private User u;
    private locationsList loc;
    private name s;
    private name f;
    private name m;



    /**
     * Populates the server's database with generated data for the specified user name. The required "username" parameter must be a user already registered with the server. If there is any data in the database already associated with the given user name, it is deleted. The optional “generations” parameter lets the caller specify the number of generations of ancestors to be generated, and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
     */
    public Fill_LoadResult fill(String username, Integer numGen) throws DataAccessException {
        Fill_LoadResult result = new Fill_LoadResult();
        db = new Database();
        Connection conn = db.getConnection();
        System.out.println("Opening connection in line 47 in fill service");
        pDao = new PersonDAO(conn);
        eDao = new EventDAO(conn);
        uDao = new UserDAO(conn);
        aDao = new AuthTokenDAO(conn);
        randomData data = readJsonfiles();
        location random = data.getLocationInfo();



        try {
            if (uDao.find(username) != null) {
                //delete all data related to this user
                pDao.clearByusername(username);
                eDao.clearByusername(username);
            }

            u = uDao.find(username);
            if(u == null){
                result.setMessage("Error: username does not exist");
                result.setSuccess(false);
                db.closeConnection(true);
                return result;
            }
            Person person = new Person(u.getPersonID(), u.getUsername(), u.getFirstName(), u.getLastName(),
                    u.getGender(), null, null, null);
            //System.out.println("Person made ");
            Event event = new Event(UUID.randomUUID().toString(), u.getUsername(), u.getPersonID(), random.getLatitude(), random.getLongitude(),
                    random.getCountry(), random.getCity(), "birth", 2000);
            eDao.insert(event);

            //createParents function(recursive)
            ArrayList<Event> listOfEvent = new ArrayList<>();
            createParents(person, event, numGen, listOfEvent);
            for(Event e : listOfEvent) {
                eDao.insert(e);
            }
            //System.out.println("Family tree generated");
            result.setMessage("Successfully added " + personNum + " persons and " + (listOfEvent.size() + 1) + " events to the database.");
            result.setSuccess(true);
            db.closeConnection(true);

        } catch (DataAccessException e) {
            result.setMessage("Error: unable to load data");
            result.setSuccess(false);
            db.closeConnection(true);
        }

        return result;
    }

    public ArrayList createParents(Person child, Event childEvent, Integer numGen, ArrayList events) throws DataAccessException {
        if (numGen == 0) {
            pDao.insert(child);
            personNum++;
            return events;
        }

        System.out.println("Generating family.......");
        randomData data = readJsonfiles(); // birth
        randomData data2 = readJsonfiles(); // marriage
        randomData data3 = readJsonfiles(); // death
        randomData data4 = readJsonfiles(); // birth
        randomData data5 = readJsonfiles(); // marriage
        randomData data6 = readJsonfiles(); // death
        Integer birth = childEvent.getYear() - 25;
        Integer marriage = birth + 20;
        Integer death = birth + 80;
        location random1 = data.getLocationInfo(); //birth, mother
        location random2 = data2.getLocationInfo(); //marriage, mother
        location random3 = data3.getLocationInfo(); //death, mother
        location random4 = data4.getLocationInfo(); //birth, father
//        location random5 = data5.getLocationInfo(); //marriage, father
        location random5 = random2; //marriage, father
        location random6 = data6.getLocationInfo(); //death, father
        String mother_personID = UUID.randomUUID().toString();
        String father_personID = UUID.randomUUID().toString();



        Person mother = new Person(mother_personID, child.getA_Username(), data.getFemale(), data.getSurname(),
                "f", null, null, null);
        Event m_Event1 = new Event(UUID.randomUUID().toString(), child.getA_Username(), mother.getPersonID(), random1.getLatitude(), random1.getLongitude(),
                random1.getCountry(), random1.getCity(), "birth", birth);
        //eDao.insert(m_Event1);
        events.add(m_Event1);
        Event m_Event2 = new Event(UUID.randomUUID().toString(), child.getA_Username(), mother.getPersonID(), random2.getLatitude(), random2.getLongitude(),
                random2.getCountry(), random2.getCity(), "marriage", marriage);
        //eDao.insert(m_Event2);
        events.add(m_Event2);
        Event m_Event3 = new Event(UUID.randomUUID().toString(), child.getA_Username(), mother.getPersonID(), random3.getLatitude(), random3.getLongitude(),
                random3.getCountry(), random3.getCity(),"death", death);
        //eDao.insert(m_Event3);
        events.add(m_Event3);
        System.out.println("Mother made");

        Person father = new Person(father_personID, child.getA_Username(), data.getMale(), data.getSurname(),
                "m", null, null, mother.getPersonID());
        Event f_Event1 = new Event(UUID.randomUUID().toString(), child.getA_Username(), father.getPersonID(), random4.getLatitude(), random4.getLongitude(),
                random4.getCountry(), random4.getCity(), "birth", birth);
        //eDao.insert(f_Event1);
        events.add(f_Event1);
        Event f_Event2 = new Event(UUID.randomUUID().toString(), child.getA_Username(), father.getPersonID(), random5.getLatitude(), random5.getLongitude(),
                random5.getCountry(), random5.getCity(), "marriage", marriage);
        //eDao.insert(f_Event2);
        events.add(f_Event2);
        Event f_Event3 = new Event(UUID.randomUUID().toString(), child.getA_Username(), father.getPersonID(), random6.getLatitude(), random6.getLongitude(),
                random6.getCountry(), random6.getCity(), "death", death);
        //eDao.insert(f_Event3);
        events.add(f_Event3);
        System.out.println("Father made");

        mother.setSpouseID(father.getPersonID());

        child.setMotherID(mother.getPersonID());
        child.setFatherID(father.getPersonID());

        pDao.insert(child);
        personNum++;
//        if (numGen == 0) {
//            return events;
//        }

        createParents(mother, m_Event1, numGen-1, events);
        createParents(father, f_Event1, numGen-1, events);

        return events;
    }

    public randomData readJsonfiles() {
        System.out.println("Reading JSON file");
        try {
            Gson gson = new Gson();
            Reader readL = Files.newBufferedReader(Paths.get("json/locations.json"));
            loc = gson.fromJson(readL, locationsList.class);
            Reader readS = Files.newBufferedReader(Paths.get("json/snames.json"));
            s = gson.fromJson(readS, name.class);
            Reader readF = Files.newBufferedReader(Paths.get("json/fnames.json"));
            f = gson.fromJson(readF, name.class);
            Reader readM = Files.newBufferedReader(Paths.get("json/mnames.json"));
            m = gson.fromJson(readM, name.class);

            int maxf = f.getData().length;
            String femaleName = f.getData()[new Random().nextInt(maxf)];
            int maxS = s.getData().length;
            String surname = s.getData()[new Random().nextInt(maxS)];
            int maxM = m.getData().length;
            String maleName = m.getData()[new Random().nextInt(maxM)];
            int maxL = loc.getData().size();
            location randomLocation = loc.getData().get(new Random().nextInt(maxL));

            randomData result = new randomData(femaleName,surname,maleName,randomLocation);

            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
