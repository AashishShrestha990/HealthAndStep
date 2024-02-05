package coventry.aashish.healthnstep;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
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


    }
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

}