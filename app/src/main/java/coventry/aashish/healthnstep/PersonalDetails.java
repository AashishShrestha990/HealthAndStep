package coventry.aashish.healthnstep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PersonalDetails extends AppCompatActivity {
    private TextView Submit;

    private ImageView Logout;
    private EditText Edittarget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Submit = (TextView) findViewById(R.id.Submitid);
        Edittarget= (EditText) findViewById(R.id.edittargetid);
        Logout= findViewById(R.id.logoutid);

        String Gettar = getIntent().getStringExtra("gettarge");
        Edittarget.setText(Gettar);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDetails.this, MainActivity.class);
                String edittarget = Edittarget.getText().toString();
                intent.putExtra("edittarge", edittarget);
                startActivity(intent);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDetails.this, LoginActivity.class);
                Toast.makeText(PersonalDetails.this, "Logout Sucessful", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}