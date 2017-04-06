package in.msritudbhav.udbhav2017insights;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VolunteerActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button Submit;
    private ProgressDialog mProgress;
    DatabaseReference volunteerRef;
    private Firebase mRef, userRef;
    private EditText NameText, PhoneText, USNText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgress=new ProgressDialog(this);
        mProgress.setCanceledOnTouchOutside(false);
        setContentView(R.layout.activity_volunteer);
        mProgress.setMessage("Please Wait...");
        mProgress.show();
        Submit = (Button) findViewById(R.id.submit_btn);
        NameText = (EditText) findViewById(R.id.name_text);
        PhoneText = (EditText) findViewById(R.id.phone_text);
        USNText = (EditText) findViewById(R.id.usn_text);
        mAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(getApplicationContext());
        mRef = new Firebase(FirebaseUtils.FirebaseURL);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null)
                {


                    startActivity(new Intent(VolunteerActivity.this, LoginActivity.class));
                }
            }
        };

        final String UID = mAuth.getCurrentUser().getUid();
        DatabaseReference baseRef = FirebaseDatabase.getInstance().getReferenceFromUrl(FirebaseUtils.FirebaseURL);
        volunteerRef = baseRef.child("users");
        volunteerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(UID)) {
                    startActivity(new Intent(VolunteerActivity.this, MainActivity.class));
                }
            }
            public void onCancelled(DatabaseError firebaseError) { }

        });

//        Log.d(TAG, "volunteer uid is "+UID);
//        if ()
//        {
//            startActivity(new Intent(LoginActivity.this, VolunteerActivity.class));
//            Log.d(TAG, "volunteer null");
//        }



        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("", "Submit clicked");
                String Phone = PhoneText.getText().toString();
                String Name = NameText.getText().toString();
                String USN = USNText.getText().toString();
                String Email = mAuth.getCurrentUser().getEmail();
                String UID = mAuth.getCurrentUser().getUid();

                if (TextUtils.isEmpty(Name)) {
                    Toast.makeText(getApplicationContext(), "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Phone)) {
                    Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(USN)) {
                    Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                VolunteerData volunteer = new VolunteerData(Name, USN, Phone, Email);
                userRef = mRef.child("users").child(UID);
                userRef.setValue(volunteer);

                   startActivity(new Intent(VolunteerActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "setvalue", Toast.LENGTH_SHORT).show();
            }
        });

        mProgress.dismiss();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }




}
