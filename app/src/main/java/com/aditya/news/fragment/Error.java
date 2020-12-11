package com.aditya.news.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;

import com.aditya.news.R;
import com.airbnb.lottie.LottieAnimationView;

public class Error extends Fragment {
    Animation top, bottom;
    LottieAnimationView lottieAnimationView;

    public Error() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        top = AnimationUtils.loadAnimation(getContext(), R.anim.top_anim);
        bottom = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_anim);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_error, container, false);
        lottieAnimationView = view.findViewById(R.id.lottieAnimationView);
        lottieAnimationView.animate().setStartDelay(4000);
        return view;
    }
}