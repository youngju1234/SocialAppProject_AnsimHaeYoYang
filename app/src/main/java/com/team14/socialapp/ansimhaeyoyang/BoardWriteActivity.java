package com.team14.socialapp.ansimhaeyoyang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team14.socialapp.ansimhaeyoyang.model.Board;
import com.team14.socialapp.ansimhaeyoyang.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BoardWriteActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseUser mUser;

    private User userInfo;
    private Board board;

    @BindView(R.id.board_content_edit_text)
    EditText et_content;
    @BindView(R.id.board_title_edit_text)
    EditText et_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
        ButterKnife.bind(this);
        setupActionBar();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }

        mUser = firebaseAuth.getCurrentUser();

        mFirebaseDatabase.getReference()
                .child("users")
                .child(mUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userInfo = new User();
                        userInfo = dataSnapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }


    @OnClick(R.id.board_write_button)
    public void onClickWriteButton(){
        String content = et_content.getText().toString();
        String title = et_title.getText().toString();

        if(content.isEmpty()||title.isEmpty()){
            Toast.makeText(getBaseContext(), "정보를 모두 입력해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }else{
            board = new Board();
            board.setContent(content);
            board.setTitle(title);
            long time = System.currentTimeMillis();
            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
            board.setDate(dayTime.format(new Date(time)));
            board.setUser(userInfo);

            mFirebaseDatabase.getReference("board")
                    .push()
                    .setValue(board)
                    .addOnSuccessListener(BoardWriteActivity.this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFirebaseDatabase.getReference("board")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                                board.setKey(snapshot.getKey());
                                            }
                                            Toast.makeText(getBaseContext(),"게시판 글쓰기 완료", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(BoardWriteActivity.this,BoardDetailActivity.class);
                                            intent.putExtra("board_data",board);
                                            startActivity(intent);
                                            finish();
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                        }
                    });
        }
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("글쓰기");
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
