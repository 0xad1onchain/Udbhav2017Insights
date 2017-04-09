package in.msritudbhav.udbhav2017insights.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import in.msritudbhav.udbhav2017insights.Utils.CustomExpandableListAdapter;
import in.msritudbhav.udbhav2017insights.R;
import in.msritudbhav.udbhav2017insights.Wrappers.EventDetail;


public class MainActivity extends AppCompatActivity {
    private DatabaseReference eventRef;
    private DatabaseReference mDatabase;
    private ArrayList<ArrayList<EventDetail>> data = new ArrayList<>();
    private ExpandableListView expandableListView;
    private CustomExpandableListAdapter expandableListAdapter;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    //finish();
                }
            }
        };

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while fetching events...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        final String UID = mAuth.getCurrentUser().getUid();
        DatabaseReference usersRef = mDatabase.child("users").child(UID);
        usersRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    Log.v("test","user does not exist");
                    dismissDialog();
                    startActivity(new Intent(MainActivity.this, VolunteerActivity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dismissDialog();
                Toast.makeText(MainActivity.this, "Check Connection. Could not get auth info", Toast.LENGTH_SHORT).show();
            }
        });

        eventRef = FirebaseDatabase.getInstance().getReference("regDescription");
        eventRef.keepSynced(true);
        eventRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dismissDialog();
                EventDetail post = dataSnapshot.getValue(EventDetail.class);
                post.setRegId(dataSnapshot.getKey());
                Log.v("event","onChildAdded: "+post.getName());
                boolean found = false;
                for(ArrayList<EventDetail> arrayList : data){
                    if(arrayList.get(0).getCatid().equals(post.getCatid())){
                        arrayList.add(post);
                        found = true;
                        break;
                    }
                }
                if(!found){
                    ArrayList<EventDetail> arrayList = new ArrayList<>();
                    arrayList.add(post);
                    data.add(arrayList);
                }
                if(expandableListAdapter!=null){
                    expandableListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                EventDetail post = dataSnapshot.getValue(EventDetail.class);
                post.setRegId(dataSnapshot.getKey());
                for(ArrayList<EventDetail> arrayList : data){
                    if(arrayList.get(0).getCatid().equals(post.getCatid())){
                        for(EventDetail detail : arrayList){
                            if(detail.getRegId().equals(post.getRegId())){
                                arrayList.remove(detail);
                                arrayList.add(post);
                                if(expandableListAdapter!=null){
                                    expandableListAdapter.notifyDataSetChanged();
                                }
                                break;
                            }
                        }
                    }
                }
                Log.v("event","onChildChanged: "+post.getName());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                EventDetail post = dataSnapshot.getValue(EventDetail.class);
                for(ArrayList<EventDetail> arrayList : data){
                    if(arrayList.get(0).getCatid().equals(post.getCatid()) && arrayList.contains(post) ){
                        arrayList.remove(post);
                        if(expandableListAdapter!=null){
                            expandableListAdapter.notifyDataSetChanged();
                        }
                        break;
                    }
                }
                Log.v("event","onChildRemoved: "+post.getName());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                EventDetail post = dataSnapshot.getValue(EventDetail.class);
                Log.v("event","onChildMoved: "+post.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dismissDialog();
                Toast.makeText(MainActivity.this, "Check Connection. Could not get event info", Toast.LENGTH_SHORT).show();
                Log.v("event","onCancelled: "+databaseError);
            }
        });
        expandableListAdapter = new CustomExpandableListAdapter(getApplicationContext(), data);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                final EventDetail expandedListItem = (EventDetail) expandableListAdapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent(getApplicationContext(), AddRegistrationActivity.class);
                intent.putExtra("EVENT_NAME", expandedListItem.getName());
                intent.putExtra("EVENT_CATNAME", expandedListItem.getCatname());
                intent.putExtra("EVENT_ID", expandedListItem.getEventid());
                intent.putExtra("EVENT_AMT", expandedListItem.getRegamt());
                intent.putExtra("EVENT_TYPE", expandedListItem.getType());
                startActivity(intent);

                return false;
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

    void dismissDialog(){
        if(progress != null && progress.isShowing()){
            progress.dismiss();
            progress = null;
        }
    }

}
