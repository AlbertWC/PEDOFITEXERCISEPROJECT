package com.example.a165727.pedofitexerciseproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a165727.pedofitexerciseproject.Sensor.StepDetector;
import com.example.a165727.pedofitexerciseproject.Sensor.StepListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main_menu extends AppCompatActivity implements SensorEventListener, StepListener{

    private TextView tv_steps,tv_date;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private Button btn_start,btn_stop;
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");

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

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numSteps = 0;
                sensorManager.registerListener(Main_menu.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

                step(numSteps);

            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sensorManager.unregisterListener(Main_menu.this);
            }
        });


        tv_date = findViewById(R.id.mainmenu_tv_date);

        //Calendar calendar = Calendar.getInstance();
     //   String formattedDate = df.format(calendar.getTime());

        tv_date.setText(df.format(new Date()));
        BroadcastReceiver BR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {



                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK)==0){

                    tv_date.setText(df.format(new Date()));

                  //  if(tv_date.equals("17:18")){

                       //Toast.makeText(Main_menu.this,"Test success",Toast.LENGTH_LONG).show();
                   // }
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
}
