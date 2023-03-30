package uz.gita.puzzle15;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uz.gita.puzzle15.localdata.MySharedPref;

public class RecordScreen extends AppCompatActivity {
    ImageView backButton;
    MySharedPref mySharedPref;
    TextView record1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_screen);

        mySharedPref=MySharedPref.getInstance();
        record1=findViewById(R.id.record1);

       
        int record=mySharedPref.getRecord1();

        record1.setText(String.valueOf(record));

        backButton=findViewById(R.id.BackButttonn);
        backButton.setOnClickListener(view -> {
            finish();
        });

    }
}