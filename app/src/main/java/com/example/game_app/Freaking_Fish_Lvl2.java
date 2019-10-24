package com.example.game_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;

public class Freaking_Fish_Lvl2 extends View {
    private Toast toast;

    public Context context;
    private Bitmap fish[] = new Bitmap[2];//to fly the fish
    //private Integer[] imgId = {R.drawable.fs1, R.drawable.back_slide3, R.drawable.back_slide1};
    private int fishX = 10;
    private int fishY;
    private int fishspeed;
    private static SoundPool soundPool;
    private static int hitsound;
    private static int oversound;
    private static int timer = 0;
    private int i = 0;
    static int soundIds[] = new int[2];
    MediaPlayer mediaPlayer;

    SharedPreferences sharedPreferences;

    private int canvasWidth, canvasHeight;

    private int yellowX, yellowy, yellowSpeed = 16;//speed of yellow ball
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed = 20;//speed of greenball
    private Paint greenPaint = new Paint();

    private int redX, redY, redSpeed = 25;//speed of greenball
    private Paint redPaint = new Paint();

    private int pinkX, pinkY,pinkSpeed=15;
    private Paint pinkPaint=new Paint();

    private int score, lifecounteroffish;// To calculate the score we intialize the varibale here score..
    private boolean touch = false;

    private Bitmap[] backgroundImage = new Bitmap[1];
    private Paint scorepaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    public Freaking_Fish_Lvl2(Context context) {

        super(context);
        //setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //Soundpool(int maxStreams, int streamType, int srcQuality)
        //soundPool=new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(attrs)
                .build();
        //int soundIds[] = new int[10];
        //soundIds[0] = soundPool.load(context, R.raw.hit, 1);

//rest of sounds goes here





        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);
        backgroundImage[0] = BitmapFactory.decodeResource(getResources(), R.drawable.ocean2);
        //backgroundImage[1] = BitmapFactory.decodeResource(getResources(), R.drawable.splash);
        //backgroundImage[2] = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        //backgroundImage[3] = BitmapFactory.decodeResource(getResources(), R.drawable.splash);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        pinkPaint.setColor(Color.CYAN);
        pinkPaint.setAntiAlias(false);


        scorepaint.setColor(Color.BLACK);
        scorepaint.setTextSize(90);
        scorepaint.setTextAlign(Paint.Align.LEFT);
        scorepaint.setStrokeWidth(5);
        //scorepaint.setElegantTextHeight(true);
        scorepaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorepaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);

        fishY = 550;
        score = 0;
        lifecounteroffish = 3;
    }

   /* private void setVolumeControlStream(int streamMusic) {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        flyingFishView.soundPool = new SoundPool.Builder()
                .setMaxStreams(2)
                .setAudioAttributes(attrs)
                .build();
        //int soundIds[] = new int[2];

    */

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        timer++;
        if(timer%100 == 0)
            i++;
        if(i>backgroundImage.length)
            i=0;
        canvas.drawBitmap(backgroundImage[(i%backgroundImage.length)], 0, 0, null);
        int minfishY = fish[0].getHeight();
        int maxfishY = canvasHeight - fish[0].getHeight() * 3;
        fishY = fishY + fishspeed;
        if (fishY < minfishY) {
            fishY = minfishY;
        }
        if (fishY > maxfishY) {
            fishY = maxfishY;
        }
        fishspeed = fishspeed + 2;
        if (touch) {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        } else {
            canvas.drawBitmap(fish[0], fishX, fishY, null);
        }


        yellowX = yellowX - yellowSpeed;


        if (hitBallChecker(yellowX, yellowy))//whenever fish get the ball thescore will be increase by 10
        {

            /*SoundPool soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
            int soundId = soundPool.load(getContext(), R.raw.hit, 1);
// soundId for reuse later on

            soundPool.play(soundId, 1, 1, 0, 0, 1);


             soundIds[0] = soundPool.load(getContext(), R.raw.hit, 1);

            soundPool.play(soundIds[0], 1, 1, 0, 0, 1);*/
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.hit);
            mediaPlayer.start();

            Toast.makeText(getContext(), "10 points", Toast.LENGTH_SHORT).show();

            score = score + 10;
            yellowX = -100;

            sharedPreferences = getContext().getSharedPreferences("Score", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("Score", score);
            editor.commit();
        }

        String nextLevel = Integer.toString(score);

        if (score>=400) {
            Log.d("Check",nextLevel.toString());
            Toast.makeText(getContext(), "highscore", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), Level3.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            getContext().startActivity(intent);
            //(MainActivity)getContext().finish();


        }



        if (yellowX < 0) {
            yellowX = canvasWidth + 21;
            yellowy = (int) Math.floor(Math.random() * (maxfishY - minfishY)) + minfishY;

        }
        canvas.drawCircle(yellowX, yellowy, 25, yellowPaint);//ball size that is radious 25
        /*it will create the yellow ball and that ball appear
         randomly on the screen at random position and fish get that ball then
         we have to add the marks or less.
         */
        greenX = greenX - greenSpeed;

        if (hitBallChecker(greenX, greenY))//whenever fish get the ball the score will be increase by 10
        {
            mediaPlayer=MediaPlayer.create(getContext(),R.raw.hit);
            mediaPlayer.start();
            Toast.makeText(getContext(), "15 points", Toast.LENGTH_SHORT).show();
            score = score + 15;
            greenX = -100;
        }
        if (greenX < 0) {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxfishY - minfishY)) + minfishY;

        }
        canvas.drawCircle(greenX, greenY, 30, greenPaint);


        pinkX = pinkX - pinkSpeed;


        if (hitBallChecker(pinkX, pinkY))//whenever fish get the ball the score will be increase by 10
        {
            mediaPlayer=MediaPlayer.create(getContext(),R.raw.hit);
            mediaPlayer.start();

            toast= Toast.makeText(getContext(), "15 points", Toast.LENGTH_SHORT);
            toast.show();

            score = score + 15;
            pinkX = -100;
        }
        if (pinkX< 0) {
            pinkX = canvasWidth + 21;
            pinkY = (int) Math.floor(Math.random() * (maxfishY - minfishY)) + minfishY;

        }
        canvas.drawCircle(pinkX, pinkY, 30, pinkPaint);

        redX = redX - redSpeed;

        if (hitBallChecker(redX, redY))//whenever fish get the ball thescore will be increase by 10
        {
            mediaPlayer=MediaPlayer.create(getContext(),R.raw.over);
            mediaPlayer.start();

            //soundIds[0] = soundPool.load(getContext(), R.raw.over, 1);
            Toast.makeText(getContext(), "lost life", Toast.LENGTH_SHORT).show();
            //score = score + 15;
            redX = -100;
            lifecounteroffish--;
            if (lifecounteroffish == 0) {

                Toast.makeText(getContext(), "GameOver", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(),GameOverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                //intent.putExtra("score",score);
                getContext().startActivity(intent);
                //getContext().bindService(Intent, ServiceConnection,int);


                // getContext().startService(intent);
                //onFinishTemporaryDetach();
                //AlertDialog.Builder builder = new AlertDialog.Builder(getContext());





            }


        }

        if (redX < 0) {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxfishY - minfishY)) + minfishY;

        }
        canvas.drawCircle(redX, redY, 32, redPaint);
        canvas.drawText("score:" + score, 20, 60, scorepaint);

        for (int i = 0; i < 3; i++) {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;
            if (i < lifecounteroffish) {

                canvas.drawBitmap(life[0], x, y, null);

            }
            else
            {
                canvas.drawBitmap(life[1], x, y, null);

            }

        }


        //canvas.drawBitmap(fish, 0, 0, null);



        // canvas.drawBitmap(life[0], 580, 10, null);
        //canvas.drawBitmap(life[0], 680, 10, null);
        //canvas.drawBitmap(life[0], 780, 10, null);
    }

    public boolean hitBallChecker(int x, int y) {
        if (fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight())) {
            return true;
        }
        return false;
    }
    /*boolean isCorrect(int answerGiven) {
        boolean correctTrueOrFalse;
        if (answerGiven == 100) {
            Toast.makeText(context.getApplicationContext(), "Well done!",
                    Toast.LENGTH_LONG).show();
            Intent k = new Intent (getContext(), Level2.class); //#######
            getContext().startActivity(k);
            correctTrueOrFalse = true;
        } else {
            correctTrueOrFalse = false;
        }
        return correctTrueOrFalse;
    }

    void updateScoreAndLevel(int answerGiven) {
        if (isCorrect(answerGiven)) {
            for (int i = 1; i <= 100; i++) {
                 score=score+i;

            }
            i++;
        }
        else {

        }
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            fishspeed = -22;

        }
        return true;
    }
    /*private Bitmap loadBitmap(int resourceID){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap tempBmp = null;
        try{
            tempBmp = BitmapFactory.decodeStream(new BufferedInputStream(getResources().openRawResource(resourceID)), null, options);
        }catch(OutOfMemoryError e){

        }catch (Error e){

        }
        return tempBmp;
    }
*/


}
