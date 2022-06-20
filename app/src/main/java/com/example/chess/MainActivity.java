package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //מאתחל את כיתה הקבועים בגודלי המסך
        DisplayMetrics dm= new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        Constants.SCREEN_HEIGHT=dm.heightPixels;
        Constants.SCREEN_WIDTH=dm.widthPixels;
        setContentView(R.layout.activity_main);

        gameView=findViewById(R.id.gv);
    }
}