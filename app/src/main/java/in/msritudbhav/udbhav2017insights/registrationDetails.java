package in.msritudbhav.udbhav2017insights;

import android.util.Log;

/**
 * Created by Adi on 06/04/17.
 */

public class registrationDetails {
    String catid, catname, eventid, maxpart, minpart, name, regamt, type;

    registrationDetails()
    {
        Log.v("Type: ",""+ this.type);
    }

    public String getCatname() {
        return catname;
    }
}
