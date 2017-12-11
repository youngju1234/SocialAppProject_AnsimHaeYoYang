package com.team14.socialapp.ansimhaeyoyang;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.team14.socialapp.ansimhaeyoyang.model.Board;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BoardDetailActivity extends AppCompatActivity {
    private Board board = new Board();
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_write_date)
    TextView tv_date;
    @BindView(R.id.tv_writer)
    TextView tv_writer;
    @BindView(R.id.board_delete)
    Button deleteButton;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        ButterKnife.bind(this);
        setupActionBar();
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        Intent intent = getIntent();
        board = (Board) intent.getSerializableExtra("board_data");
        tv_title.setText(board.getTitle());
        tv_content.setText(board.getContent());
        tv_date.setText(board.getDate());
        tv_writer.setText(board.getUser().getUserName());
        key = board.getKey();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser=mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();


        if(!mFirebaseUser.getUid().equals(board.getUser().getUserUID())){
            deleteButton.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.board_delete)
    public void deleteBoard(){

        DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mFirebaseUser.getUid().equals(board.getUser().getUserUID())) {
                    mFirebaseDatabase.getReference("board/"+ key).removeValue();
                    startActivity(new Intent(BoardDetailActivity.this,BoardActivity.class));
                }
            }
        };
        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("게시글을 삭제하시겠습니까?")
                .setPositiveButton("삭제", deleteListener)
                .setNegativeButton("취소", cancelListener)
                .show();

    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("게시글 상세 내용");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(BoardDetailActivity.this,BoardActivity.class));
                    finish();
                }
            });
        }
    }
}
