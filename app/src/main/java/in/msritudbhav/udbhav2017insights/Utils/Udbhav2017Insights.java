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
        Log.v("Udbhav","Application created");
        Firebase.setAndroidContext(getApplicationContext());
        //Firebase.getDefaultConfig().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
