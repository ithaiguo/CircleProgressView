package com.example.circleview;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private CustomCircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleView = findViewById(R.id.circleview);
        ObjectAnimator animator = ObjectAnimator.ofFloat(circleView,"progress",0,80f);
        animator.setDuration(5000);
        animator.start();
    }
}
