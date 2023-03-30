package uz.gita.puzzle15;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);
        ImageView backImage=findViewById(R.id.backButtton);
        backImage.setOnClickListener(v->{
            finish();
        });

    }
}