package in.msritudbhav.udbhav2017insights.Wrappers;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Adi on 05/04/17.
 */
@IgnoreExtraProperties
public class VolunteerData {
    public String name;
    public String usn;
    public String phoneno;
    public String email;

    VolunteerData()
    {}

    public VolunteerData(String Name, String USN, String Phone, String Email)
    {
        this.name = Name;
        this.email = Email;
        this.phoneno = Phone;
        this.usn = USN;

    }
}
