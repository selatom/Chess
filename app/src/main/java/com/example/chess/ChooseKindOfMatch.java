package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class ChooseKindOfMatch extends AppCompatActivity {
    static boolean multi = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_kind_of_match);

        ImageView single = findViewById(R.id.btn_single);
        ImageView multiplayer = findViewById(R.id.btn_multi);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btn_single){
                    multi = true;
                }

                Intent intent =new Intent(ChooseKindOfMatch.this, MainActivity.class);
                startActivity(intent);
            }
        };

        single.setOnClickListener(onClickListener);
        multiplayer.setOnClickListener(onClickListener);
    }
}