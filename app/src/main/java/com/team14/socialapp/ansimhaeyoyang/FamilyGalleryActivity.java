package com.team14.socialapp.ansimhaeyoyang;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team14.socialapp.ansimhaeyoyang.model.GalleryItem;

import java.util.ArrayList;
import java.util.List;

public class FamilyGalleryActivity extends AppCompatActivity {

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
        mFirebaseDatabase.getReference("family_gallery")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot gallerySnapshot: dataSnapshot.getChildren()) {
                            GalleryItem galleryItem =gallerySnapshot.getValue(GalleryItem.class);
                            galleryItems.add(galleryItem);

                        }
                        ArrayList<GalleryItem> temp = new ArrayList<GalleryItem>();
                        for(int i=galleryItems.size()-1;i>0;i--){
                            temp.add(galleryItems.get(i));
                        }

                        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), temp, R.layout.activity_family_gallery));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FamilyGalleryActivity.this, FamilyGalleryWriteActivity.class));
            }
        });

    }


    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("보호자 소식");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent( FamilyGalleryActivity.this,MainActivity.class));
                    finish();
                }
            });
        }
    }


}
