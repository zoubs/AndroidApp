package com.example.myapplication.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.UserViewHolder> {

    private List<UserInformation> users;
    private Context context;

    public RVAdapter(Context context, List<UserInformation> users) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public RVAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_linear_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapter.UserViewHolder holder, int position) {
        if(users.get(position).getIsAdmin()) {
            holder.img.setImageResource(R.mipmap.ic_admin);
        }
        else {
            holder.img.setImageResource(R.mipmap.ic_user);
        }

        holder.tvEmail.setText(users.get(position).getEmail());
        holder.tvName.setText(users.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView img;
        TextView tvName;
        TextView tvEmail;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.user_photo);
            tvEmail = itemView.findViewById(R.id.user_email);
            tvName = itemView.findViewById(R.id.user_name);

//            cv.setRadius(8);//设置图片圆角的半径大小
//            cv.setCardElevation(8);//设置阴影部分大小
//            cv.setContentPadding(5,5,5,5);//设置图片距离阴影大小
        }
    }
}
