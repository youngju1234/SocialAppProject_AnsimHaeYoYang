package com.team14.socialapp.ansimhaeyoyang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.team14.socialapp.ansimhaeyoyang.model.Program;
import com.team14.socialapp.ansimhaeyoyang.model.User;

import java.util.ArrayList;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Program> items = new ArrayList<>();
    private ArrayList<User> participants = new ArrayList<>();
    private int item_layout;
    private int type=0;//요양프로그램 관리 = 1; 요양 프로그램 안내 = 2;

    public ProgramAdapter(Context context, ArrayList<Program> items, int item_layout , int type) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
        this.type = type;
    }

    @Override
    public ProgramAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = 0;
        switch (type){
            case 1:
                layout = R.layout.item_admin_program;
                break;
            case 2:
                //layout = R.layout.item_participate_program;
                break;
        }

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ProgramAdapter.ViewHolder holder = new ProgramAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ProgramAdapter.ViewHolder holder, final int position) {
        holder.date.setText(items.get(position).getDate());
        holder.time.setText(items.get(position).getTime());
        holder.title.setText(items.get(position).getTitle());
        //holder.participant_num.setText(items.get(position).getParticipants().size());

        final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseDatabase.getReference("program/"+ items.get(position).getKey()).removeValue();
            }
        });
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
        public ViewHolder(View v) {
            super(v);
            date = (TextView) v.findViewById(R.id.item_date);
            time = (TextView) v.findViewById(R.id.item_time);
            title = (TextView) v.findViewById(R.id.item_title);
            participant_num = (TextView) v.findViewById(R.id.item_participant_num);
            delete = (Button) v.findViewById(R.id.item_delete);
        }
    }
}

