package com.example.myapplication.ui.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.util.RecyclerViewSpacesItemDecoration;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserManageFragment extends Fragment {

    private RecyclerView recyclerView;
    static List<UserInformation> users;
    private RVAdapter rvAdapter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_info,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        users = new ArrayList<>();
        users.clear();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //连接到mysql
                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://39.101.211.144:3306/android_db?useSSL=false&allowPublicKeyRetrieval=true",
                            "android",
                            "android123456");
                    String sql = "select username,userEmail,is_admin from users";

                    PreparedStatement psmt = conn.prepareStatement(sql);

                    ResultSet rs = psmt.executeQuery();

                    while(rs.next()) {
                        users.add(new UserInformation(
                                rs.getString("username"),
                                rs.getString("userEmail"),
                                rs.getBoolean("is_admin")));
                    }

                    conn.close();
                    psmt.close();
                    rs.close();

                } catch (ClassNotFoundException e) {
                    Log.d("error","链接失败");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerView = view.findViewById(R.id.user_info_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAdapter = new RVAdapter(getActivity(),users);
        recyclerView.setAdapter(rvAdapter);

        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,10);//top间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,10);//底部间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,20);//左间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,20);//右间距

        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
    }

    public void addUser(UserInformation newUser) {
        if(rvAdapter != null) {
            rvAdapter.addDataAt(newUser);
        }
    }
}
