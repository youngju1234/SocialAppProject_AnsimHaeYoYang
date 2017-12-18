package com.team14.socialapp.ansimhaeyoyang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.team14.socialapp.ansimhaeyoyang.model.Program;
import com.team14.socialapp.ansimhaeyoyang.model.User;

import java.util.ArrayList;
import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Program> items = new ArrayList<>();
    private ArrayList<User> participants = new ArrayList<>();
    private int item_layout;
    private int type=0;//요양프로그램 관리 = 1; 요양 프로그램 안내 = 2;
    private User userInfo;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private List<Boolean> flags = new ArrayList<>();

    public ProgramAdapter(Context context, ArrayList<Program> items, int item_layout , int type) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
        this.type = type;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public ProgramAdapter(Context context, ArrayList<Program> items,List<Boolean> flags, int item_layout , int type) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
        this.type = type;
        this.flags=flags;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public ProgramAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = 0;
        switch (type){
            case 1:
                layout = R.layout.item_admin_program;
                break;
            case 2:
                layout = R.layout.item_participate_program;
                break;
        }

        getCurrentUserInfo();

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ProgramAdapter.ViewHolder holder = new ProgramAdapter.ViewHolder(v);
        return holder;
    }

    private void getCurrentUserInfo() {
        mFirebaseDatabase.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid())
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

    @Override
    public void onBindViewHolder(ProgramAdapter.ViewHolder holder, final int position) {
        holder.date.setText(items.get(position).getDate());
        holder.time.setText(items.get(position).getTime());
        holder.title.setText(items.get(position).getTitle());

        if(!flags.isEmpty()){
            if (flags.get(position))
                holder.participate.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
        }

        if(items.get(position).getUsers()!=null)
            holder.participant_num.setText(items.get(position).getUsers().size()+" 명");

        if(type==1){
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mFirebaseDatabase.getReference("program/"+ items.get(position).getKey()).removeValue();
                }
            });
        }
        if(type==2){
            holder.participate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(flags.get(position)){
                        mFirebaseDatabase.getReference("program/"+items.get(position).getKey()+"/participants/"+userInfo.getUserUID()).removeValue();
                        flags.set(position,false);
                    }else{
                        mFirebaseDatabase.getReference("program/"+items.get(position).getKey()+"/participants/"+userInfo.getUserUID()).setValue(userInfo);
                        flags.set(position,true);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(items.isEmpty())
            return 0;
        else
            return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView participant_num;
        TextView time;
        Button delete;
        Button participate;
        public ViewHolder(View v) {
            super(v);
            date = (TextView) v.findViewById(R.id.item_date);
            time = (TextView) v.findViewById(R.id.item_time);
            title = (TextView) v.findViewById(R.id.item_title);
            participant_num = (TextView) v.findViewById(R.id.item_participant_num);
            if(type==1)
                delete = (Button) v.findViewById(R.id.item_delete);
            if(type==2)
                participate = (Button)v.findViewById(R.id.item_participate_button);
        }
    }
}

