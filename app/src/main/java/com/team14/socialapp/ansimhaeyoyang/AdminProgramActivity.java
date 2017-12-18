package com.team14.socialapp.ansimhaeyoyang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team14.socialapp.ansimhaeyoyang.model.Program;
import com.team14.socialapp.ansimhaeyoyang.model.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminProgramActivity extends AppCompatActivity {

    @BindView(R.id.admin_program_recycler_view)
    RecyclerView recyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private static ArrayList<Program> itemArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_program);
        ButterKnife.bind(this);
        setupActionBar();

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
                    ArrayList<User> users = new ArrayList();
                    for (DataSnapshot s : fileSnapshot.child("participants").getChildren()) {
                        User u = s.getValue(User.class);
                        users.add(u);
                    }
                    p.setKey(fileSnapshot.getKey());
                    p.setUsers(users);
                    itemArrayList.add(p);
                }
                recyclerView.setAdapter(new ProgramAdapter(AdminProgramActivity.this, itemArrayList, R.layout.activity_admin_program,1));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("요양 프로그램 관리");
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

    @OnClick(R.id.fab)
    public void onclickAddProgram(){
        Intent intent = new Intent(getApplicationContext(), AdminAddProgramActivity.class);
        startActivity(intent);
        finish();
    }
}
