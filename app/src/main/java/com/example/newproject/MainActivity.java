package com.example.newproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager=null;
    Sensor stepsensor;
    public int totalstep=0;
    public int previewsstep=0;
    public int currentstep=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        stepsensor=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        BottomNavigationView bottomNavigationView=findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id=item.getItemId();
                if(id==R.id.Home)
                {
                    fragloader(new mainscreen());
                }
                if(id==R.id.community)
                {
                    fragloader(new Community());
                }
                if(id==R.id.global)
                {
                    fragloader(new Globalchat());
                }
                if(id==R.id.mypage)
                {
                    fragloader(new Page());
                }
                return true;
            }
            public void fragloader(Fragment frag)
            {
                FragmentManager fm =getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.framelayout,frag);
                ft.commit();
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.Home);
    }
    protected void onResume() {

        super.onResume();
        if(stepsensor==null){
            Toast.makeText(this, "This device has no sensor", Toast.LENGTH_SHORT).show();
        }
        else{
            sensorManager.registerListener(this,stepsensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    protected void onPause() {

        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_STEP_COUNTER)
        {
            totalstep=(int) event.values[0];
            currentstep=totalstep-previewsstep;
//            steps.setText(String.valueOf(currentstep));
//            progressBar.setProgress(currentstep);
        }
    }

//    private void resetstep(){
//        steps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Long Press to reset Steps", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        steps.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                previewsstep=totalstep;
////                saveData();
//                return true;
//            }
//        });
//    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}