package in.msritudbhav.udbhav2017insights.Wrappers;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Adi on 06/04/17.
 */

@IgnoreExtraProperties
public class EventDetail {
    private String catid, catname, eventid, maxpart, minpart, name, regamt, type;
    private String regId;
    EventDetail()
    {
        Log.v("Type: ",""+ this.type);
    }

    @Override
    public boolean equals(Object obj) {
        EventDetail val = (EventDetail) obj;
        if(catid.equalsIgnoreCase(val.catid) && catname.equalsIgnoreCase(val.catname)
                && eventid.equalsIgnoreCase(val.eventid) && name.equalsIgnoreCase(val.name))
            return true;
        return super.equals(obj);
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getMaxpart() {
        return maxpart;
    }

    public void setMaxpart(String maxpart) {
        this.maxpart = maxpart;
    }

    public String getMinpart() {
        return minpart;
    }

    public void setMinpart(String minpart) {
        this.minpart = minpart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegamt() {
        return regamt;
    }

    public void setRegamt(String regamt) {
        this.regamt = regamt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}
