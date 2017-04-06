package in.msritudbhav.udbhav2017insights;

/**
 * Created by Adi on 05/04/17.
 */

public class VolunteerData {
    String name;
    String usn;
    String phoneno;
    String email;

    VolunteerData()
    {}

    VolunteerData(String Name, String USN, String Phone, String Email)
    {
        this.name = Name;
        this.email = Email;
        this.phoneno = Phone;
        this.usn = USN;

    }
}
