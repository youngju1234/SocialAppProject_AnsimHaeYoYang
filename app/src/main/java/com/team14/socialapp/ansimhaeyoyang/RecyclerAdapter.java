package com.team14.socialapp.ansimhaeyoyang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.team14.socialapp.ansimhaeyoyang.model.GalleryItem;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<GalleryItem> items;
    private int item_layout;

    public RecyclerAdapter(Context context, ArrayList<GalleryItem> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = new CustomCardView(parent.getContext());
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final GalleryItem item = items.get(position);
        CustomCardView cardView = holder.cardView;
        cardView.setData(item);

        cardView.setUserActionListener(new CustomCardView.UserActionListener() {
            @Override
            public void onImageClicked() {
                Toast.makeText(context, "image " + position, Toast.LENGTH_SHORT).show();
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
        CustomCardView cardView;

        public ViewHolder(View card) {
            super(card);
            cardView = (CustomCardView) card;
        }
    }
}
