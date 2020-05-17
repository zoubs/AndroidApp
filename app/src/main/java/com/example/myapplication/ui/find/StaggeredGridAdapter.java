package com.example.myapplication.ui.find;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;


public class StaggeredGridAdapter extends RecyclerView.Adapter<StaggeredGridAdapter.StaggeredGridViewHolder> {

    private Context mContext;

    public StaggeredGridAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public StaggeredGridAdapter.StaggeredGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new StaggeredGridViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_staggered_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StaggeredGridAdapter.StaggeredGridViewHolder holder, int position) {
        holder.textView.setText("Hello world!");
        if((position % 2) == 1) {
            holder.imageView.setImageResource(R.drawable.image1);
            holder.linearLayout.setBackground(mContext.getDrawable( R.drawable.test_image_border2));
        }
        else {
            holder.imageView.setImageResource(R.drawable.image2);
            //holder.linearLayout.setBackgroundColor(Color.parseColor("#98FB98"));
            holder.linearLayout.setBackground(mContext.getDrawable( R.drawable.test_image_border1));
        }

    }

    @Override
    public int getItemCount() {
        return 30;
    }


    class StaggeredGridViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;
        private LinearLayout linearLayout;

        public StaggeredGridViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_title);
            imageView = itemView.findViewById(R.id.iv_img);
            linearLayout = itemView.findViewById(R.id.item_layout);
        }
    }
}
