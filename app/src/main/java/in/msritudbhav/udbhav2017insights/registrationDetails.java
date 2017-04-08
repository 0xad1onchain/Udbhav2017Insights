package in.msritudbhav.udbhav2017insights;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Adi on 06/04/17.
 */

@IgnoreExtraProperties
public class registrationDetails {
    public String catid, catname, eventid, maxpart, minpart, name, regamt, type;

    registrationDetails()
    {
        Log.v("Type: ",""+ this.type);
    }

    public String getCatname() {
        return catname;
    }
}
