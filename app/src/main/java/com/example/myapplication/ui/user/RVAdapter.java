package com.example.myapplication.ui.user;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MyUsers;
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
    public void onBindViewHolder(@NonNull final RVAdapter.UserViewHolder holder, final int position) {
        if(users.get(position).getIsAdmin()) {
            holder.img.setImageResource(R.mipmap.ic_admin);
        }
        else {
            holder.img.setImageResource(R.mipmap.ic_user);
        }

        holder.tvEmail.setText(users.get(position).getEmail());
        holder.tvName.setText(users.get(position).getName());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String userName = holder.tvName.getText().toString();
                final String userEmail = holder.tvEmail.getText().toString();

                //定义弹窗
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View view = LayoutInflater.from(context).inflate(R.layout.alter_user_info_dialog, null);
                builder.setTitle("用户："+userEmail).setView(view);
                final AlertDialog dialog = builder.create();
                final EditText alterName = view.findViewById(R.id.alter_user_name);
                final RadioGroup radioGroup =view.findViewById(R.id.alter_user_is_admin);

                alterName.setText(userName);

                //Button confirm
                Button btn_confirm = view.findViewById(R.id.btn_user_confirm);
                btn_confirm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        int is_admin = 0;
                        String name;

                        //修改用户名
                        name = alterName.getText().toString();

                        //判断选择
                        RadioButton rb1 = (RadioButton)radioGroup.getChildAt(0);
                        RadioButton rb2 = (RadioButton)radioGroup.getChildAt(1);
                        if(rb1.isChecked()) {
                            is_admin = 1;
                        }
                        else if(rb2.isChecked()) {
                            is_admin = -1;
                        }
                        else {
                            is_admin = 0;
                        }

                        MyUsers myUsers_1 = new MyUsers();
                        myUsers_1.userAlter(userEmail, name, is_admin);
                        Toast.makeText(context, "修改完成！", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                //Button Delete
                Button btn_delete = view.findViewById(R.id.btn_user_delete);
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "this is delete!", Toast.LENGTH_SHORT).show();
                        //删除
                        final MyUsers myUsers = new MyUsers();
                        myUsers.userDelete(userEmail);

                        //调用加载条
                        final PopupWindow popupWindow;
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                        final View popupWindow_view = inflater.inflate(R.layout.alter_progressbar_layout,null,false);
                        popupWindow = new PopupWindow(popupWindow_view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,false);
                        popupWindow.showAsDropDown(v);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                popupWindow.dismiss();
                                //插入成功

                                if(!myUsers.isUserEmailExist(userEmail)) {
                                    Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
                                    removeDataAt(position);
                                    dialog.cancel();
                                }
                                //插入失败
                                else {
                                    Toast.makeText(context, "删除失败，请重试！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 3000);//3秒后执行Runnable中的run方法
                        //dialog.cancel();
                    }
                });

                //Button Cancel
                Button btn_cancel = view.findViewById(R.id.btn_user_cancel);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                dialog.show();
                //Toast.makeText(context, "hey!"+username, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    //删除（动画）
    public boolean removeDataAt(int position) {
        if(position >= users.size()) {
            return false;
        }
        users.remove(position);
        notifyItemRemoved(position);
        return true;
    }

    //添加（动画）
    public void addDataAt(UserInformation data) {
        users.add(data);
        notifyItemInserted(users.size() - 1);//更新数据集，注意如果用adapter.notifyDataSetChanged()将没有动画效果
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
