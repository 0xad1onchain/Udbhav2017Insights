package in.msritudbhav.udbhav2017insights.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import in.msritudbhav.udbhav2017insights.R;
import in.msritudbhav.udbhav2017insights.Wrappers.RegHolder;
import in.msritudbhav.udbhav2017insights.Wrappers.RegistrationData;

public class HistoryActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<RegistrationData, RegHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_history);
        recyclerView = (RecyclerView) findViewById(R.id.historyRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(HistoryActivity.this, EmailLoginActivity.class));
                    finish();
                }
            }
        };

        Query historyQuery = FirebaseDatabase.getInstance().getReference("registrations")
                .orderByChild("vid")
                .equalTo(mAuth.getCurrentUser().getEmail());
        /*
        historyQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            Log.i("MyTag", child.getValue().toString());
                            RegistrationData reg = child.getValue(RegistrationData.class);
                            if(reg != null){
                                Log.v("history", "Name: "+reg.getName());
                                Log.v("history", "Email: "+reg.getEmail());
                                Log.v("history", "College: "+reg.getCollege());
                                Log.v("history", "Event IDs: "+reg.getEventId());
                            }else{
                                Log.v("history", "conversion failed");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        */
        mAdapter = new FirebaseRecyclerAdapter<RegistrationData, RegHolder>(RegistrationData.class, R.layout.history_row, RegHolder.class, historyQuery) {
            @Override
            protected void populateViewHolder(RegHolder viewHolder, RegistrationData model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setAmount(model.getAmount());
                viewHolder.setEventName(model.getEventName());
                viewHolder.setCollegeName(model.getCollege());
            }
        };

        DividerItemDecoration verticalDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.HORIZONTAL);
        Drawable verticalDivider = ContextCompat.getDrawable(this, R.drawable.vertical_divider);
        verticalDecoration.setDrawable(verticalDivider);
        recyclerView.addItemDecoration(verticalDecoration);

        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(this, R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        recyclerView.addItemDecoration(horizontalDecoration);



        recyclerView.setAdapter(mAdapter);
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
