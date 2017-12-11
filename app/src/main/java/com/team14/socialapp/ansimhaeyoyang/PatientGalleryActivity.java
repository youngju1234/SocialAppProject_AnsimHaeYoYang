package com.team14.socialapp.ansimhaeyoyang;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team14.socialapp.ansimhaeyoyang.model.GalleryItem;

import java.util.ArrayList;

public class PatientGalleryActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private ArrayList<GalleryItem> galleryItems;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_gallery);

        setupActionBar();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser=mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        galleryItems = new ArrayList<GalleryItem>();
        mFirebaseDatabase.getReference("sanatorium_gallery")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot gallerySnapshot: dataSnapshot.getChildren()) {
                            GalleryItem galleryItem =gallerySnapshot.getValue(GalleryItem.class);
                            galleryItems.add(galleryItem);

                        }

                        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), galleryItems, R.layout.activity_family_gallery));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (mFirebaseUser.getUid().equals(Constants.ADMIN_UID)) {


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(PatientGalleryActivity.this, PatientGalleryWriteActivity.class));
                }
            });
        }else{
            fab.setVisibility(View.GONE);
        }
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("요양원 소식");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent( PatientGalleryActivity.this,MainActivity.class));
                    finish();
                }
            });
        }
    }
}
