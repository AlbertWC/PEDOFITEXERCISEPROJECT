package com.example.a165727.pedofitexerciseproject;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a165727.pedofitexerciseproject.Entities.StepsHistory;
import com.example.a165727.pedofitexerciseproject.Sensor.StepDetector;
import com.example.a165727.pedofitexerciseproject.Sensor.StepListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import static android.support.v4.content.ContextCompat.startActivity;

public class Main_menu extends AppCompatActivity implements SensorEventListener, StepListener{

    private TextView tv_steps,tv_date,tv_time;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private Button btn_start,btn_stop,btn_mainmenu_history,btn_screenshot;
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    private final SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");
    private final SimpleDateFormat df3 = new SimpleDateFormat("dd");
    private BroadcastReceiver BR;

    private int day;
    private int distance;

    public History history;
    public static MyStepHistoryDB myStepHistoryDB;

    File imagePath;


    @Override
    protected void onStart() {
        super.onStart();


        sensorManager.registerListener(Main_menu.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

        step(numSteps);
    }

    public void Permission(){

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(Main_menu.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Main_menu.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(Main_menu.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);



        tv_steps = (TextView) findViewById(R.id.mainmenu_tv_pedometer);
        btn_start = (Button) findViewById(R.id.main_btn_start);
        btn_stop = (Button) findViewById(R.id.main_btn_stop);
        history = new History();
        btn_mainmenu_history = findViewById(R.id.mainmenu_btn_history);

       //screenshot button
        btn_screenshot = findViewById(R.id.main_btn_screenshot);

        Permission();


        myStepHistoryDB = Room.databaseBuilder(Main_menu.this, MyStepHistoryDB.class, "historyDB").build();

  /*      btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numSteps = 0;
                sensorManager.registerListener(Main_menu.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

                step(numSteps);
            }
        });*/


        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorManager.unregisterListener(Main_menu.this);
                Intent intent = new Intent(Main_menu.this, History.class);
                startActivity(intent);
            }
        });
        btn_mainmenu_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance = (numSteps-1) * 5;
                Intent intent_mainmenu_history = new Intent(Main_menu.this , History.class);
                saveHistory();
                startActivity(intent_mainmenu_history);
            }
        });

        btn_screenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Bitmap bitmap = takescreenshot();
            save(bitmap);
            share();
            }
        });
        tv_date = findViewById(R.id.mainmenu_tv_date);
        tv_time = findViewById(R.id.mainmenu_tv_time);


        //Calendar calendar = Calendar.getInstance();
     //   String formattedDate = df.format(calendar.getTime());

        tv_date.setText(df.format(new Date()));
        tv_time.setText(df2.format(new Date()));
        day = Integer.parseInt(df3.format(new Date()));

        BR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent2) {

               // Toast.makeText(Main_menu.this,"Test success toast",Toast.LENGTH_LONG).show();

                if (intent2.getAction().compareTo(Intent.ACTION_TIME_TICK)==0){

                    tv_date.setText(df.format(new Date()));
                    tv_time.setText(df2.format(new Date()));

                    if(df2.format(new Date()).equals("00:00")){

                        unregisterReceiver(BR);
                        distance = (numSteps-1) * 5;
                      // Toast.makeText(Main_menu.this,"Test success "+ "day "+day+"numStep "+(numSteps-1)+"distance "+ distance,Toast.LENGTH_LONG).show();
                     //  history.saveHistory(day,numSteps,distance);

                     /*  Intent intent = new Intent(Main_menu.this, History.class);
                       intent.putExtra("dayKey", day);
                       intent.putExtra("stepKey", numSteps-1);
                       intent.putExtra("distanceKey", distance);

                       startActivity(intent);*/
                        saveHistory();
                    }
                }
            }
        };

        registerReceiver(BR, new IntentFilter(Intent.ACTION_TIME_TICK));

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    sensorEvent.timestamp, sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void step(long timeNs) {

        tv_steps.setText(TEXT_NUM_STEPS + numSteps);
        numSteps++;
    }
    public void saveHistory(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                StepsHistory stepsHistory = new StepsHistory(day,numSteps,distance);;
                myStepHistoryDB.historyDao().insertHistory(stepsHistory);
            }
        }).start();


    }

    public Bitmap takescreenshot(){
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    public void share(){

        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");

        //chg the text here
        String shareBody = "Hey,check out my Pedofit scores";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Pedofit ");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        // Intent send = new Intent(Intent.createChooser(sharingIntent,"Share Via"));
        startActivity(Intent.createChooser(sharingIntent,"Share Via"));
        //Intent.createChooser(sharingIntent,"Share Via");
    }
    public void save(Bitmap bitmap){

        imagePath = new File(Environment.getExternalStorageDirectory()+"/screenshot.png");
        FileOutputStream fos;
        try{

            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e){
            Log.e("GREC",e.getMessage(),e);
        } catch (IOException e){
            Log.e("GREC",e.getMessage(),e);
        }
    }
}
