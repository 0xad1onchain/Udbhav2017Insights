package in.msritudbhav.udbhav2017insights;

import java.util.ArrayList;

/**
 * Created by Adi on 07/04/17.
 */

public class RegistrationData {
    String name, college, phone, email, amount, vid;
    ArrayList<String> eventIds = new ArrayList<String>();
    Boolean emailSent;



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
        this.eventIds.add(0, events);
        this.vid = vid;
        emailSent = false;
    }
}
