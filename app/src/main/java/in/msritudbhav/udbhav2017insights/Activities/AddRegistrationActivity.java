package in.msritudbhav.udbhav2017insights.Activities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.msritudbhav.udbhav2017insights.R;
import in.msritudbhav.udbhav2017insights.Wrappers.RegistrationData;
import in.msritudbhav.udbhav2017insights.Wrappers.EventDetail;

public class AddRegistrationActivity extends AppCompatActivity {
    private String eventName, eventId, eventAmt, eventCatName, eventType;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EventDetail obj;
    private TextView detail;
    private Button submit;
    private EditText name, phone, email, college;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_registration);
        mAuth = FirebaseAuth.getInstance();
        eventName = getIntent().getStringExtra("EVENT_NAME");
        eventId = getIntent().getStringExtra("EVENT_ID");
        eventAmt = getIntent().getStringExtra("EVENT_AMT");
        eventType = getIntent().getStringExtra("EVENT_TYPE");
        eventCatName = getIntent().getStringExtra("EVENT_CATNAME");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(getApplicationContext());
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    startActivity(new Intent(AddRegistrationActivity.this, LoginActivity.class));
                }
            }
        };

        if(eventType == null || eventType.equals(null) || eventType.equals("null"))
        {
            eventType = "Not Available";
        }

        name = (EditText) findViewById(R.id.name_text);
        detail = (TextView) findViewById(R.id.detail_text);
        phone = (EditText) findViewById(R.id.phone_text);
        email = (EditText) findViewById(R.id.email_text);
        college = (EditText) findViewById(R.id.college_text);
        submit = (Button) findViewById(R.id.add_btn);

        detail.setText("Event: "+ eventName+"\nAmount: "+eventAmt+"\nCategory: "+ eventCatName + "\nType: "+eventType );

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("", "Add clicked");
                String Phone = phone.getText().toString();
                String Name = name.getText().toString();
                String College = college.getText().toString();
                String Email = email.getText().toString();
                String vid = mAuth.getCurrentUser().getEmail();
                String amount = eventAmt;
                String events = eventId;

                if (TextUtils.isEmpty(Name)) {
                    Toast.makeText(getApplicationContext(), "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Phone)) {
                    Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(College)) {
                    Toast.makeText(getApplicationContext(), "Enter College Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog progress;
                progress = new ProgressDialog(AddRegistrationActivity.this);
                progress.setTitle("Loading");
                progress.setMessage("Wait while loading...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();

                RegistrationData reg = new RegistrationData(Name, College, Phone, Email, amount, events, vid);
                DatabaseReference userRef = mDatabase.child("registrations");

                userRef.push().setValue(reg, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(progress != null && progress.isShowing())
                            progress.dismiss();
                        if(databaseError != null)
                        {
                            Log.v("udbhav17",databaseError.toString());
                            Toast.makeText(getApplicationContext(), "Upload Failed, Check Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                });
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
