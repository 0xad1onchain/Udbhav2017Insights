package in.msritudbhav.udbhav2017insights;

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
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddRegistrationActivity extends AppCompatActivity {
    private String EventName;
    private Firebase ref;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth mAuth;
    private registrationDetails obj;
    private TextView detail;
    private Button Submit;
    private EditText name, phone, email, college;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_registration);
        mAuth = FirebaseAuth.getInstance();
        EventName = getIntent().getStringExtra("EVENT_NAME");
        ref = new Firebase(FirebaseUtils.FirebaseURL);
        Query eventRef = ref.child("regDescription").orderByChild("name").equalTo(EventName);

        name = (EditText) findViewById(R.id.name_text);
        detail = (TextView) findViewById(R.id.detail_text);
        phone = (EditText) findViewById(R.id.phone_text);
        email = (EditText) findViewById(R.id.email_text);
        college = (EditText) findViewById(R.id.college_text);
        Submit = (Button) findViewById(R.id.add_btn);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(AddRegistrationActivity.this, LoginActivity.class));
                    //finish();
                }
            }
        };

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("Count ", "" + dataSnapshot.getChildrenCount());


                for (com.firebase.client.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    registrationDetails post = postSnapshot.getValue(registrationDetails.class);
                    Log.e("Get Data", "GotIT"+post.name);
                    obj = post;
                    detail.setText("Event: "+ obj.name+"\nAmount: "+obj.regamt+"\nCategory: "+ obj.catname );




                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("The read failed: ", "Error invoked onCalcelled");

            }


        });


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("", "Add clicked");
                String Phone = phone.getText().toString();
                String Name = name.getText().toString();
                String College = college.getText().toString();
                String Email = email.getText().toString();
                String vid = mAuth.getCurrentUser().getEmail();
                String amount = obj.regamt;
                String events = obj.eventid;

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

                RegistrationData reg = new RegistrationData(Name, College, Phone, Email, amount, events, vid);
                Firebase userRef = ref.child("registrations");

                userRef.push().setValue(reg);


                Toast.makeText(getApplicationContext(), "setvalue", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
