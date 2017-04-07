package in.msritudbhav.udbhav2017insights;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    DatabaseReference mRef;
    private HashMap<String, ArrayList<registrationDetails>> catToEvent;
    private FirebaseListAdapter mAdapter;
    private ArrayList<Categories> categoriesList;
    private ArrayList<registrationDetails> eventList;
    private ListView mListView;
    private Firebase ref, catRef, eventRef;
    List<String> Art = new ArrayList<String>();
    List<String> Dance = new ArrayList<String>();
    List<String> Misc = new ArrayList<String>();
    List<String> Music = new ArrayList<String>();
    List<String> Literature = new ArrayList<String>();
    List<String> Theme = new ArrayList<String>();
    HashMap<String, ArrayList<registrationDetails>> expandableListDetail = new HashMap<>();
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    ArrayList<String> expandableListTitle = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoriesList = new ArrayList<Categories>();
        eventList = new ArrayList<registrationDetails>();
        catToEvent = new HashMap<>();
        auth = FirebaseAuth.getInstance();
      Firebase.setAndroidContext(getApplicationContext());
        ref = new Firebase(FirebaseUtils.FirebaseURL);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        final ArrayList<Categories> catnameList = new ArrayList<>();
        authListener = new FirebaseAuth.AuthStateListener() {
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

        final String UID = auth.getCurrentUser().getUid();
        Firebase voRef = ref.child("users");

        voRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(UID)) {

                }
                else
                {
                    startActivity(new Intent(MainActivity.this, VolunteerActivity.class));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("The read failed: ", "Error invoked onCalcelled");

            }


        });


//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        catRef = ref.child("categories");
//
//        catRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                Log.e("Count ", "" + dataSnapshot.getChildrenCount());
//
//                categoriesList.clear();
//
//                for (com.firebase.client.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Categories post = postSnapshot.getValue(Categories.class);
//                    Log.e("Get Data", "GotIT"+post.name);
//                    categoriesList.add(post);
//                    catnameList.add(post);
//
//
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Log.e("The read failed: ", "Error invoked onCalcelled");
//
//            }
//
//
//        });

        eventRef = ref.child("regDescription");

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                Log.e("Count ", "" + dataSnapshot.getChildrenCount());

                catToEvent.clear();

                for (com.firebase.client.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    registrationDetails post = postSnapshot.getValue(registrationDetails.class);
                    Log.e("Get Data", "GotIT" + post.name + post.catid);
                    eventList.add(post);
                    ArrayList<registrationDetails> eventObjectList = catToEvent.get(post.catid);
                    if(eventObjectList == null)
                    {
                        eventObjectList = new ArrayList<registrationDetails>();

                    }

                    eventObjectList.add(post);
                    catToEvent.put(post.catid, eventObjectList);
                    if(!expandableListTitle.contains(post.getCatname()))
                    {
                        expandableListTitle.add(post.getCatname());
                    }



                }


                    Iterator it  = catToEvent.entrySet().iterator();
                    while(it.hasNext())
                    {
                        Map.Entry<String, ArrayList<registrationDetails>> obj = (Map.Entry) it.next();
                        ArrayList<registrationDetails> catname = obj.getValue();
                        expandableListDetail.put(catname.get(0).getCatname(), catname);
                    }




//                    expandableListDetail.put("Art", Art);
//                    expandableListDetail.put("Dance", Dance);
//                    expandableListDetail.put("Misc", Misc);
//                    expandableListDetail.put("Music", Music);
//                    expandableListDetail.put("Theme", Theme);
//                    expandableListDetail.put("Literature", Literature);
//                    expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                    expandableListAdapter = new CustomExpandableListAdapter(getApplicationContext(), expandableListTitle,expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);
                    expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                        @Override
                        public void onGroupExpand(int groupPosition) {
//                            Toast.makeText(getApplicationContext(),
//                                    expandableListTitle.get(groupPosition) + " List Expanded.",
//                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                        @Override
                        public void onGroupCollapse(int groupPosition) {
//                            Toast.makeText(getApplicationContext(),
//                                    expandableListTitle.get(groupPosition) + " List Collapsed.",
//                                    Toast.LENGTH_SHORT).show();

                        }
                    });

                    expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v,
                                                    int groupPosition, int childPosition, long id) {
//                            Toast.makeText(
//                                    getApplicationContext(),
//                                    expandableListTitle.get(groupPosition)
//                                            + " -> "
//                                            + expandableListDetail.get(
//                                            expandableListTitle.get(groupPosition)).get(
//                                            childPosition), Toast.LENGTH_SHORT
//                            ).show();


                            final registrationDetails expandedListItem = (registrationDetails) expandableListAdapter.getChild(groupPosition, childPosition);
                            Intent intent = new Intent(getApplicationContext(), AddRegistrationActivity.class);
                            intent.putExtra("EVENT_NAME", expandedListItem.name);
                            intent.putExtra("EVENT_CATNAME", expandedListItem.catname);
                            intent.putExtra("EVENT_ID", expandedListItem.eventid);
                            intent.putExtra("EVENT_AMT", expandedListItem.regamt);
                            intent.putExtra("EVENT_TYPE", expandedListItem.type);
                            startActivity(intent);

                            return false;
                        }
                    });
                }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("The read failed: ", "Error invoked onCalcelled");

            }


        });
    }

}
