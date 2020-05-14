package com.example.myapplication.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class HomePageFragment extends Fragment {
    public static HomePageFragment newInstance() {
        Bundle args = new Bundle();

        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }
}