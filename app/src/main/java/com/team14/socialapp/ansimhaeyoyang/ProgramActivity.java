package com.team14.socialapp.ansimhaeyoyang;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team14.socialapp.ansimhaeyoyang.model.Board;
import com.team14.socialapp.ansimhaeyoyang.model.Program;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ProgramActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private static ArrayList<Program> itemArrayList = new ArrayList<>();
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        setupActionBar();
        recyclerView = (RecyclerView)  findViewById(R.id.prgram_recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("program");

       databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemArrayList.clear();
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    Program p=  fileSnapshot.getValue(Program.class);
                    itemArrayList.add(p);
                }
                recyclerView.setAdapter(new ProgramAdapter(getApplicationContext(), itemArrayList, R.layout.activity_program));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton writeprogram = (FloatingActionButton)findViewById(R.id.fab);
        writeprogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProgramWriteActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("요양 프로그램 안내");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

}
