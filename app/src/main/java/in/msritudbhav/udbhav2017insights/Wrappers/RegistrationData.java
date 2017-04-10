package in.msritudbhav.udbhav2017insights.Wrappers;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by Adi on 07/04/17.
 */
@IgnoreExtraProperties
public class RegistrationData {
    public String name, college, phone, email, amount, vid;
    public ArrayList<String> eventIds = new ArrayList<String>();
    public Boolean emailSent;
    public String eventName;
    public String eventId, catid;



    RegistrationData()
    {

    }

    public RegistrationData(String name, String eventName, String catid, String college, String phone, String email, String amount, String eventId, String vid )
    {
        this.name = name;
        this.eventName = eventName;
        this.eventId = eventId;
        this.catid = catid;
        this.college = college;
        this.phone = phone;
        this.email = email;
        this.amount = amount;
        this.eventIds.add(0, eventId);
        this.vid = vid;
        emailSent = false;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public ArrayList<String> getEventIds() {
        return eventIds;
    }

    public void setEventIds(ArrayList<String> eventIds) {
        this.eventIds = eventIds;
    }

    public Boolean getEmailSent() {
        return emailSent;
    }

    public void setEmailSent(Boolean emailSent) {
        this.emailSent = emailSent;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
