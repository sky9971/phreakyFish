package com.example.game_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
   private  flyingFishView btn_pause;
    //ViewFlipper imgFlipper;
    private flyingFishView gameview;
    private flyingFishView imageView;
    private Handler handler=new Handler();
    private final static long Interval=30;
    private DisplayMetrics metrics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        ImageView slide1=findViewById(R.id.background_slide1);
        //AnimationDrawable animationDrawable=(AnimationDrawable)slide1.getDrawable();
        //animationDrawable.start();


        gameview=new flyingFishView(this);
        setContentView(gameview);





        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                handler.post(
                        new Runnable() {
                    @Override
                    public void run() {
                        //startActivity(new Intent().setClass(MainActivity.this, Level2 .class).setData(getIntent().getData()));
                        //finish();
                        //Intent i = new Intent(MainActivity.this, Level2.class);
                        //startActivity(i);

                        //setContentView(R.layout.activity_level2);
                        gameview.invalidate();



                    }
                });
            }
        },500,Interval);


        

        //500,Interval

       /* imgFlipper=findViewById(R.id.imgflipper);
        setContentView(imageView);
        int sliders[]={
                R.drawable.fs1,
                R.drawable.images,
                R.drawable.fs3
        };
        for( int slide: sliders){
            sliderFlipper(slide);
        }*/
    }
    public void onClick(View v) {
        new Thread(new Runnable() {
            public void run() {
                // a potentially  time consuming task
                Intent intent=new Intent(MainActivity.this,Level2.class);
                startActivity(intent);


                }

        }).start();
    }





    /*public void sliderFlipper(int image){
         imageView=new flyingFishView(this);
        imageView.setBackgroundResource(image);
        imgFlipper.addView(imageView);
        imgFlipper.setFlipInterval(5000);
        imgFlipper.setAutoStart(true);
        imgFlipper.setInAnimation(this,android.R.anim.fade_in);
        imgFlipper.setOutAnimation(this,android.R.anim.fade_out);
    }*/
}


