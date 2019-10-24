package com.example.game_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Level;

public class Level_Screen extends AppCompatActivity {
    Button btnlvl1, btnlvl2, btnlvl3, btninstruction;

    TextView tv_score;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_level__screen);


        btnlvl1 = findViewById(R.id.btn_lvl1);
        btnlvl2 = findViewById(R.id.btn_lvl2);
        btnlvl3 = findViewById(R.id.btn_lvl3);
        tv_score = findViewById(R.id.tv_score);

        btninstruction = findViewById(R.id.btn_instruction);

        sharedPreferences = getSharedPreferences("Score", Context.MODE_PRIVATE);
        final int score = sharedPreferences.getInt("Score", 0);
        String scr = Integer.toString(score);
        tv_score.setText("Score is: " + scr);

        btnlvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Level_Screen.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });

        btnlvl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (score>=200){
                    Intent intent=new Intent(Level_Screen.this,Level2.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Level_Screen.this, "Please try again, You did not achieve the score..", Toast.LENGTH_SHORT).show();
                }


                //Intent intent=new Intent(Level_Screen.this,Level2.class);
                // startActivity(intent);

            }
        });

        btnlvl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (score>=400){
                    Intent intent=new Intent(Level_Screen.this,Level3.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Level_Screen.this, "Please try again, You did not achieve the score..", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btninstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Level_Screen.this, Instructions.class);
                startActivity(intent);

            }
        });

    }


}

