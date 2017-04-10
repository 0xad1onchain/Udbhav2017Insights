package in.msritudbhav.udbhav2017insights.Utils;

import android.app.Application;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by abhilash1in on 9/4/17.
 */

public class Udbhav2017Insights extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("udbhav","Application created");
        Firebase.setAndroidContext(getApplicationContext());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference("regDescription").keepSynced(true);
        FirebaseDatabase.getInstance().getReference("registrations").keepSynced(true);
    }
}
