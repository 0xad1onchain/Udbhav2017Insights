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
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.msritudbhav.udbhav2017insights.Utils.FirebaseUtils;
import in.msritudbhav.udbhav2017insights.R;
import in.msritudbhav.udbhav2017insights.Wrappers.VolunteerData;

public class VolunteerActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button submit;
    private ProgressDialog mProgress;
    private EditText nameText, phoneText, USNText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgress=new ProgressDialog(this);
        mProgress.setCanceledOnTouchOutside(false);
        setContentView(R.layout.activity_volunteer);
        mProgress.setMessage("Please Wait...");
        mProgress.show();
        submit = (Button) findViewById(R.id.submit_btn);
        nameText = (EditText) findViewById(R.id.name_text);
        phoneText = (EditText) findViewById(R.id.phone_text);
        USNText = (EditText) findViewById(R.id.usn_text);
        mAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(getApplicationContext());
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    startActivity(new Intent(VolunteerActivity.this, LoginActivity.class));
                }
            }
        };

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progress;
                progress = new ProgressDialog(VolunteerActivity.this);
                progress.setTitle("Loading");
                progress.setMessage("Wait while loading...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();
                Log.v("", "submit clicked");
                String phone = phoneText.getText().toString();
                String name = nameText.getText().toString();
                String USN = USNText.getText().toString();
                String email = mAuth.getCurrentUser().getEmail();
                String UID = mAuth.getCurrentUser().getUid();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(USN)) {
                    Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                VolunteerData volunteer = new VolunteerData(name, USN, phone, email);
                DatabaseReference userRef = mDatabase.child("users").child(UID);
                userRef.setValue(volunteer, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(progress!= null && progress.isShowing())
                            progress.dismiss();
                        if(databaseError != null){
                            Toast.makeText(VolunteerActivity.this, "Error saving volunteer data", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(VolunteerActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(VolunteerActivity.this, MainActivity.class));
                        }
                    }
                });
            }
        });

        mProgress.dismiss();

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
