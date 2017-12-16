package com.team14.socialapp.ansimhaeyoyang;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team14.socialapp.ansimhaeyoyang.model.Program;

import java.util.ArrayList;

/**
 * Created by YB on 2017-12-12.
 */

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Program> items = new ArrayList<Program>();
    private int item_layout;
    public ProgramAdapter(Context context, ArrayList<Program> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }
    @Override
    public ProgramAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program, parent, false);

        ProgramAdapter.ViewHolder holder = new ProgramAdapter.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ProgramAdapter.ViewHolder holder, final int position) {
        holder.name.setText(items.get(position).getProgram_name());
        holder.date.setText(items.get(position).getDate());
        holder.num.setText(Integer.toString(items.get(position).getNum()));
        holder.time.setText(items.get(position).getTime());
    }

    @Override
    public int getItemCount() {

        if(items.isEmpty())
            return 0;
        else
            return this.items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView date;
        public TextView num;
        public TextView time;
        public ViewHolder(View card) {
            super(card);
            name = (TextView) card.findViewById(R.id.item_pro_name);
            date = (TextView) card.findViewById(R.id.item_date);
            num = (TextView) card.findViewById(R.id.item_pro_num);
            time = (TextView) card.findViewById(R.id.item_time);
        }
    }
}
