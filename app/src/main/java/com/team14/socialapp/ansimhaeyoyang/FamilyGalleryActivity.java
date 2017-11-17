package com.team14.socialapp.ansimhaeyoyang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.team14.socialapp.ansimhaeyoyang.model.GalleryItem;

import java.util.ArrayList;
import java.util.List;

public class FamilyGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_gallery);
        setupActionBar();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<GalleryItem> items = new ArrayList<>();
        GalleryItem[] item = new GalleryItem[4];
        item[0] = new GalleryItem(R.drawable.family4,"2017/11/17","1보고싶어요!!", "###님의 가족","작성자 : 이영주");
        item[1] = new GalleryItem(R.drawable.family3,"2017/11/16","2보고싶어요!!", "###님의 가족","작성자 : 이영주");
        item[2] = new GalleryItem(R.drawable.family2,"2017/11/15","3보고싶어요!!", "###님의 가족","작성자 : 이영주");
        item[3] = new GalleryItem(R.drawable.family1,"2017/11/14","4보고싶어요!!", "###님의 가족","작성자 : 이영주");

        for (int i = 0; i <4; i++){
            items.add(item[i]);
        }

        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_family_gallery));
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
                    finish();
                }
            });
        }
    }
}
