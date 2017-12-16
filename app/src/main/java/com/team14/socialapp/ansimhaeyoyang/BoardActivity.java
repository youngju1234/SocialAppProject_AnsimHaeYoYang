package com.team14.socialapp.ansimhaeyoyang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team14.socialapp.ansimhaeyoyang.model.Board;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private static ArrayList<Board> itemArrayList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        setupActionBar();
        recyclerView = (RecyclerView)  findViewById(R.id.my_recycler_view);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser=mAuth.getCurrentUser();

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("board");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemArrayList.clear();
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    Board readBoard= fileSnapshot.getValue(Board.class);
                    readBoard.setKey(fileSnapshot.getKey());
                    itemArrayList.add(readBoard);
                }
                recyclerView.setAdapter(new BoardAdapter(getApplicationContext(), itemArrayList, R.layout.activity_board));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Button writeboard = (Button)findViewById(R.id.button_write_board);

        if (!mFirebaseUser.getUid().equals(Constants.ADMIN_UID)) {

            writeboard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), BoardWriteActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else{
            writeboard.setVisibility(View.GONE);
        }
    }



    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("게시글 목록");
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
