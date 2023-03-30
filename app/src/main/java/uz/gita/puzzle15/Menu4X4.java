package uz.gita.puzzle15;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Menu4X4 extends AppCompatActivity {

    TextView startGame;
    AppCompatButton aboutGame;
    AppCompatButton recordGame;
    AppCompatButton quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu4_x4);
        startGame=findViewById(R.id.startGamee);
        aboutGame=findViewById(R.id.about);
        recordGame=findViewById(R.id.result);
        quit=findViewById(R.id.quitGame4);
        startGame.setOnClickListener(v->{
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        });
        aboutGame.setOnClickListener(v->{
            Intent intent=new Intent(this,AboutScreen.class);
            startActivity(intent);
        });

        recordGame.setOnClickListener(v->{
            Intent intent=new Intent(this,RecordScreen.class);
            startActivity(intent);
        });

        quit.setOnClickListener(v->{
            finish();
        });

    }
}