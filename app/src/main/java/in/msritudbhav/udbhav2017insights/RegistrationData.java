package in.msritudbhav.udbhav2017insights;

import java.util.ArrayList;

/**
 * Created by Adi on 07/04/17.
 */

public class RegistrationData {
    String name, college, phone, email, amount, vid;
    ArrayList<String> events = new ArrayList<String>();


    RegistrationData()
    {

    }

    RegistrationData(String name, String college, String phone, String email, String amount, String events, String vid )
    {
        this.name = name;
        this.college = college;
        this.phone = phone;
        this.email = email;
        this.amount = amount;
        this.events.add(0, events);
        this.vid = vid;
    }
}
