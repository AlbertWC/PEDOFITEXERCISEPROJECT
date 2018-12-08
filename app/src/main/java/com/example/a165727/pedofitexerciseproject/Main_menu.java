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

    private TextView tv_steps,tv_date,tv_time;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private Button btn_start,btn_stop;
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    private final SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");
    private final SimpleDateFormat df3 = new SimpleDateFormat("dd");
    private BroadcastReceiver BR;

    private int day;
    private int distance;

    public History history;


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



               // sensorManager.unregisterListener(Main_menu.this);
              //  Intent intent = new Intent(Main_menu.this, History.class);
              //  startActivity(intent);
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

                    if(df2.format(new Date()).equals("20:33")){

                        unregisterReceiver(BR);
                        distance = (numSteps-1) * 5;
                      // Toast.makeText(Main_menu.this,"Test success "+ "day "+day+"numStep "+(numSteps-1)+"distance "+ distance,Toast.LENGTH_LONG).show();
                     //  history.saveHistory(day,numSteps,distance);

                       Intent intent = new Intent(Main_menu.this, History.class);
                       intent.putExtra("dayKey", day);
                       intent.putExtra("stepKey", numSteps-1);
                       intent.putExtra("distanceKey", distance);

                       startActivity(intent);
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
}
