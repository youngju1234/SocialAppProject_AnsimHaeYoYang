package com.team14.socialapp.ansimhaeyoyang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team14.socialapp.ansimhaeyoyang.model.Board;
import com.team14.socialapp.ansimhaeyoyang.model.User;

import java.util.ArrayList;

/**
 * Created by YB on 2017-12-08.
 */

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Board> items = new ArrayList<Board>();
    private int item_layout;
    private FirebaseDatabase mFirebaseDatabase;

    public BoardAdapter(Context context, ArrayList<Board> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }/*
    @Override
    public void onClick(View v){
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.getReference()
                .child("board")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Board board = dataSnapshot.getValue(Board.class);
                        board.getKey();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }*/


    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);

        ViewHolder holder = new ViewHolder(v);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"gd", Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(BoardAdapter.ViewHolder holder, final int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.date.setText(items.get(position).getDate());
        holder.writer.setText(items.get(position).getUser().getUserName());
    }

    @Override
    public int getItemCount() {
        if(items.isEmpty())
            return 0;
        else
            return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;
        public TextView writer;
        public ViewHolder(View card) {
            super(card);
            title = (TextView) card.findViewById(R.id.info_name);
            date = (TextView) card.findViewById(R.id.date);
            writer = (TextView) card.findViewById(R.id.user);
        }
    }
}
