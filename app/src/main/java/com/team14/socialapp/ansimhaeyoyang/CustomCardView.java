package com.team14.socialapp.ansimhaeyoyang;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.team14.socialapp.ansimhaeyoyang.model.GalleryItem;

/**
 * Created by dudwn on 2017-11-17.
 */

public class CustomCardView extends CardView {

    private CardView cardView;
    private TextView titleTextview;
    private TextView dateTextview;
    private ImageView imageView;
    private TextView contentTextview;
    private TextView writerTextview;

    private UserActionListener mUserActionListener;

    public interface UserActionListener {
        public void onImageClicked();
    }

    public CustomCardView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CustomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void setUserActionListener(UserActionListener l) {
        this.mUserActionListener = l;
    }

    public void setData(GalleryItem item) {
        Glide.with(getContext()).load(item.getImage()).into(imageView);
        titleTextview.setText(item.getTitle());
        contentTextview.setText(item.getContent());
        writerTextview.setText(item.getWriter());
        dateTextview.setText(item.getDate());

    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.item_cardview, this, true);

        cardView = (CardView) findViewById(R.id.cardview);
        imageView = (ImageView) findViewById(R.id.iv_image);
        titleTextview = (TextView) findViewById(R.id.tv_title);
        contentTextview = (TextView) findViewById(R.id.tv_content);
        writerTextview = (TextView) findViewById(R.id.tv_writer);
        dateTextview = (TextView) findViewById(R.id.tv_date);

        cardView.setOnClickListener(mOnClickListener);
        imageView.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(mUserActionListener == null) {
                return;
            }

            if(v == imageView) {
                mUserActionListener.onImageClicked();
            }
        }
    };
}
