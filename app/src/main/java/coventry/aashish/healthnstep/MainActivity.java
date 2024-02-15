package coventry.aashish.healthnstep;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView Target, Waterlevel,Distance,Steps;
    private TextView Dates,Calorie,Time,WaterBtn;

    private String Edittar = "6000";
    private TextView D1,D2,D3,D4,D5,D6,D7;
    private SensorManager sensorManager;
    private Sensor stepCountSensor;
    private int stepCount = 0;
    private int previousStep = 0;
    private int waterlevel=0;

    private ImageView Settarget;
    private ProgressBar stepprogressBar,waterprogress, dailyProgress;

    private ProgressBar s1,s2,s3,s4,s5,s6,s7;
    private boolean isPause = false;
    private long timePaused = 0;
    private float stepLengthinmeter = 0.76f;
    private float calorieperstep = 0.04f;
    private long startTime;
    private int stepCountTarget = 8000;

    private TextView stepCounttargetView;

    protected  void onStop() {
        super.onStop();
        if (stepCountSensor != null){
            sensorManager.unregisterListener(this);
            timerHandler.removeCallbacks(timerRunnable);
        }
    }
    protected void onResume() {
        super.onResume();
        if (stepCountSensor != null){
            sensorManager.registerListener( this, stepCountSensor, SensorManager.SENSOR_DELAY_NORMAL);
            timerHandler.postDelayed(timerRunnable, 0);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Steps = findViewById(R.id.stepsid);
        Distance = findViewById(R.id.distanceid);
        Calorie = findViewById(R.id.calorieid);
        Target = findViewById(R.id.timeid);
        Time = findViewById(R.id.minuteid);
        WaterBtn=findViewById(R.id.button);
        Waterlevel=findViewById(R.id.waterlevelid);
        //    stepCounttargetView=findViewById(R.id.stepcount);
        stepprogressBar = findViewById(R.id.progresBar);
        waterprogress = findViewById(R.id.waterid);
        dailyProgress = findViewById(R.id.step7);
        D1=findViewById(R.id.d1);
        D2=findViewById(R.id.d2);
        D3=findViewById(R.id.d3);
        D4=findViewById(R.id.d4);
        D5=findViewById(R.id.d5);
        D6=findViewById(R.id.d6);
        D7=findViewById(R.id.d7);
        Settarget=findViewById(R.id.settargetid);

        Edittar = getIntent().getStringExtra("edittarge");
        if (Edittar == null) {

            Edittar = "7000";
        }
        stepCountTarget = Integer.parseInt(Edittar);
        Dates = findViewById(R.id.dateid);
        resetsteps();
        loadData();
        startTime  = System.currentTimeMillis();

        Settarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTargetFunction();
            }
        });

        dailyProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Stepcount is "+ stepCount, Toast.LENGTH_SHORT).show();
            }
        });

        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
        String dateonly = getdateonly();
        int previous  = Integer.parseInt(dateonly)-1;
        int previous1  = Integer.parseInt(dateonly)-2;
        Dates.setText(currentDate);

        s1=findViewById(R.id.step1);
        s2=findViewById(R.id.step2);
        s3=findViewById(R.id.step3);
        s4=findViewById(R.id.step4);
        s5=findViewById(R.id.step5);
        s6=findViewById(R.id.step6);
        s7=findViewById(R.id.step7);
        sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        int previous2  = Integer.parseInt(dateonly)-3;
        int previous3  = Integer.parseInt(dateonly)-4;

        stepprogressBar.setMax(stepCountTarget);
        s7.setMax(9000);
        waterprogress.setMax(2000);
        Target.setText("/" + stepCountTarget);

        if (stepCountSensor == null){
            Target.setText("no sensor");
        }
        WaterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterprogress.incrementProgressBy(200);
                waterlevel = waterlevel+ 200;
                Waterlevel.setText(waterlevel+"/2000ml");

            }

        });
        int previous4  = Integer.parseInt(dateonly)-5;
        int previous5  = Integer.parseInt(dateonly)-6;

        D7.setText("2/"+dateonly);
        D6.setText("2/"+previous);
        D5.setText("2/"+previous1);
        D4.setText("2/"+previous2);
        D3.setText("2/"+previous3);
        D2.setText("2/"+previous4);
        D1.setText("2/"+previous5);

    }
    public void setTargetFunction() {
//        Dialog dialog = new Dialog();
//        dialog.show();
        Intent intent = new Intent(MainActivity.this, PersonalDetails.class);
        String target = Target.getText().toString();
        String Target1= String.valueOf(stepCountTarget);
        intent.putExtra("gettarge", Target1);
        startActivity(intent);
    }

    private String getdateonly(){
        return new SimpleDateFormat("dd",Locale.getDefault()).format(new Date());
    };

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            stepCount = (int) sensorEvent.values[0];
            int currentSteps = stepCount-previousStep;
            Steps.setText("" + currentSteps);
            stepprogressBar.setProgress(currentSteps);
            s7.setProgress(currentSteps);
            waterprogress.setProgress(waterlevel);
//            waterprogress,set(currentSteps);
            if(stepCount>=stepCountTarget){
//                stepCounttargetView.setText("Achived");
            }
            float distanceinkm = currentSteps * stepLengthinmeter/1000;
            Distance.setText(String.format(Locale.getDefault(),"%.1f Km", distanceinkm));

            float calorie = currentSteps * stepLengthinmeter * calorieperstep;
            Calorie.setText(String.format(Locale.getDefault(),"%.0f Cal", calorie));
        }
    }

    private void resetsteps(){
        Steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "longpress to reset", Toast.LENGTH_SHORT).show();
            }
        });

        Steps.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                previousStep= stepCount;
                Steps.setText("0");
                stepprogressBar.setProgress(0);
                saveData();
                return true;
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedpre = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpre.edit();
        editor.putString("Key1",String.valueOf(previousStep));
        editor.apply();
    }

    public void applyTexts(int target) {
        Target.setText(target);
    }

    private void loadData(){
        SharedPreferences sharedpre = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        int savedNumber  = (int) sharedpre.getFloat("key1", 0f);
        previousStep = savedNumber;
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
    public void onPauseButtonclicked(View view) {
        if (isPause) {
            isPause = false;
//        PauseBtn.setText("Resume");

        }
    }

}