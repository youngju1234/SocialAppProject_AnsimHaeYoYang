package com.team14.socialapp.ansimhaeyoyang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class BoardActivity extends AppCompatActivity {
    static final String[] LIST_MENU = {"식대비 관련 안내 드립니다. ", "정신건강을 위한 10가지 수칙!", "마사지 꿀팁", "마사지 꿀팁", "마사지 꿀팁", "마사지 꿀팁", "마사지 꿀팁", "마사지 꿀팁", "마사지 꿀팁", "마사지 꿀팁", "마사지 꿀팁", "마사지 꿀팁", "마사지 꿀팁", "마사지 꿀팁", "마사지 꿀팁", "마사지 꿀팁"} ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        setupActionBar();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = (ListView) findViewById(R.id.Boardlistview) ;
        listview.setAdapter(adapter) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재화면의 제어권자
                        BoardDetailActivity.class); //
                startActivity(intent);


                // get TextView's Text.

            }
        }) ;

        Button writeboard = (Button)findViewById(R.id.button_write_board);
        writeboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoardWriteActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
