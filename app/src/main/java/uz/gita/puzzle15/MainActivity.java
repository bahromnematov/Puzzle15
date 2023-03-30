package uz.gita.puzzle15;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uz.gita.puzzle15.localdata.MySharedPref;
import uz.gita.puzzle15.model.Coordinate;


public class MainActivity extends AppCompatActivity {
    TextView texScore;
    Chronometer texTime;
    RelativeLayout containerButtons;
    AppCompatButton[][] buttons;
    private long pauseTime;
    Coordinate emptyCoordinate;
    List<Integer> numbers;
    int counter = 0;
    AppCompatImageView btnRefresh;
    MySharedPref mySharedPref;
    AppCompatImageView musicVisible;
    AppCompatImageView musicInVisible;
    MediaPlayer mediaPlayer;
    long reordeTime;
    Button restartButton;
    MediaPlayer mediaClick;
    AppCompatImageView back;
    boolean isTrue;
    int record1;
    boolean musicTrue;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("PPP", "onDestroy ");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("PPP", "onCreate");
        initView();
        initNumber();
        loadNumbersToButtons();
        continueOrNewGame();

        texTime.start();

        btnRefresh.setOnClickListener(v -> {
            refreshGame();
        });
        musicVisible.setOnClickListener(v -> {
            musicVisible.setVisibility(View.INVISIBLE);
            musicTrue = false;
            musicInVisible.setVisibility(View.VISIBLE);
            mySharedPref.setClickBtn(musicTrue);
        });
        musicInVisible.setOnClickListener(v -> {
            musicInVisible.setVisibility(View.INVISIBLE);
            musicTrue = true;
            musicVisible.setVisibility(View.VISIBLE);
            mySharedPref.setClickBtn(musicTrue);
        });

        if (mySharedPref.getClickBtn()) {
            musicVisible.setVisibility(View.VISIBLE);
            musicInVisible.setVisibility(View.INVISIBLE);
        } else {
            musicVisible.setVisibility(View.INVISIBLE);
            musicInVisible.setVisibility(View.VISIBLE);
        }


        mySharedPref.setIsTrue(musicTrue);


    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("PPP", "onSaveInstanceState");
        outState.putInt("COUNT", counter);
        outState.putBoolean("ISTRUE", isTrue);
        outState.putLong("PAUSETIME", pauseTime);
        outState.putBoolean("Click", mySharedPref.getClickBtn());
        texTime.setText(String.valueOf(SystemClock.elapsedRealtime() - pauseTime));
        texTime.start();
        ArrayList<String> numbers = new ArrayList<>();
        for (int i = 0; i < containerButtons.getChildCount(); i++) {
            numbers.add(((Button) containerButtons.getChildAt(i)).getText().toString());

        }
        outState.putStringArrayList("numbers", numbers);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("PPP", "onRestoreInstanceState");
        isTrue = savedInstanceState.getBoolean("ISTRUE");
        counter = savedInstanceState.getInt("COUNT", 0);
        texScore.setText(String.valueOf(counter));
        pauseTime = SystemClock.elapsedRealtime() - texTime.getBase();
        pauseTime = savedInstanceState.getLong("PAUSETIME", 0);
        musicTrue = savedInstanceState.getBoolean("Click", false);
        texTime.setBase(SystemClock.elapsedRealtime() - pauseTime);
        texTime.start();
        texTime.setText(String.valueOf(SystemClock.elapsedRealtime() - pauseTime));
        List<String> numbersList = savedInstanceState.getStringArrayList("numbers");
        for (int i = 0; i < numbersList.size(); i++) {
            Log.d("TTT", numbersList.get(numbersList.size() - 1) + "");
        }
        loadSavedNumbers(numbersList);
    }

    private void loadSavedNumbers(List<String> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).equals("")) {
                Log.d("LLL", i + "");

                emptyCoordinate = new Coordinate(i / 4, i % 4);
                buttons[i / 4][i % 4].setVisibility(View.INVISIBLE);
                if (i / 4 != 3 && i % 4 != 3) {
                    buttons[3][3].setVisibility(View.VISIBLE);
                }
            }
            buttons[i / 4][i % 4].setText(numbers.get(i));
            if (buttons[3][3].getText() != "") {
                buttons[3][3].setVisibility(View.VISIBLE);
            }
        }
    }


    private void initView() {
        texScore = findViewById(R.id.text_score);
        texTime = findViewById(R.id.text_time);
        btnRefresh = findViewById(R.id.btnRefresh);
        musicInVisible = findViewById(R.id.stopMusic);
        musicVisible = findViewById(R.id.Music);
        back = findViewById(R.id.BackButttonn);
        mediaClick = new MediaPlayer();
        mediaClick = MediaPlayer.create(this, R.raw.click);
        mediaPlayer = new MediaPlayer();
        mySharedPref = MySharedPref.getInstance();
        mediaPlayer = MediaPlayer.create(this, R.raw.game_music2);
        mySharedPref = MySharedPref.getInstance();
        containerButtons = findViewById(R.id.containerButtons);

        back.setOnClickListener(v -> {
            finish();
        });

        restartButton = findViewById(R.id.btnDialogRestart);
        int count = containerButtons.getChildCount();
        int size = (int) Math.sqrt(count);
        buttons = new AppCompatButton[size][size];
        for (int i = 0; i < count; i++) {
            int x = i / 4;
            int y = i % 4;
            AppCompatButton button = (AppCompatButton) containerButtons.getChildAt(i);
            button.setTag(new Coordinate(x, y));
            button.setOnClickListener(this::onClick);
            buttons[x][y] = button;
        }
        emptyCoordinate = new Coordinate(size - 1, size - 1);
    }


    private void initNumber() {
        int count = containerButtons.getChildCount();
        numbers = new ArrayList<>(count - 1);
        for (int i = 1; i < count; i++) {
            numbers.add(i);
        }
    }


    private void loadNumbersToButtons() {
        shuffleNumber();
        buttons[emptyCoordinate.getX()][emptyCoordinate.getY()].setVisibility(View.VISIBLE);
        emptyCoordinate.setX(3);
        emptyCoordinate.setY(3);
        buttons[emptyCoordinate.getX()][emptyCoordinate.getY()].setVisibility(View.INVISIBLE);
        buttons[3][3].setText("");
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                int index = i * buttons.length + j;
                if (index < 15) {
                    buttons[i][j].setText(String.valueOf(numbers.get(index)));
                }
            }
        }

        texTime.setBase(SystemClock.elapsedRealtime());
        texTime.start();
        counter = 0;
        texScore.setText(String.valueOf(counter));
    }

    void refreshGame() {
        counter = 0;
        shuffleNumber();
        loadNumbersToButtons();
    }

    void shuffleNumber() {
         Collections.shuffle(numbers);
    }

    boolean isWin() {
        for (int i = 0; i < 15; i++) {
            String s = buttons[i / 4][i % 4].getText().toString();
            if (!s.equals(String.valueOf(i + 1))) {
                return false;
            }
        }
        return true;
    }

    private void onClick(View view) {
        AppCompatButton button = (AppCompatButton) view;
        Coordinate clickedCoordinate = (Coordinate) button.getTag();
        int dx = Math.abs(clickedCoordinate.getX() - emptyCoordinate.getX());
        int dy = Math.abs(clickedCoordinate.getY() - emptyCoordinate.getY());
        if (dx + dy == 1) {
            Log.d("TTT", mySharedPref.getClickBtn() + "");
            if (mySharedPref.getClickBtn()) {
                mediaClick.start();
            } else {
                if (mediaClick.isPlaying()) {
                    mediaClick.pause();
                }
            }
            texScore.setText(String.valueOf(++counter));
            buttons[emptyCoordinate.getX()][emptyCoordinate.getY()].setText(button.getText());
            buttons[emptyCoordinate.getX()][emptyCoordinate.getY()].setVisibility(View.VISIBLE);
            button.setText("");
            emptyCoordinate.setX(clickedCoordinate.getX());
            emptyCoordinate.setY(clickedCoordinate.getY());
            buttons[emptyCoordinate.getX()][emptyCoordinate.getY()].setVisibility(View.INVISIBLE);
            if (isWin()) {
                reordeTime = (SystemClock.elapsedRealtime() - texTime.getBase()) / (60);
                mySharedPref.setPauseTime(reordeTime);
                CustomDialog customDialog = new CustomDialog();
                customDialog.setCancelable(false);
                FragmentManager fm = getSupportFragmentManager();
                customDialog.setCancelable(false);
                customDialog.show(fm, null);
                record1 = counter;
                mySharedPref.setRecord1(counter);
                texTime.stop();
                customDialog.setOnClickListener(new CustomDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        refreshGame();
                    }
                });
            }
        }
    }

    private void continueOrNewGame() {
        boolean isPlaying = mySharedPref.getPlaying();
        initNumber();
        if (isPlaying) {
            pauseTime = mySharedPref.getPauseTime();
            counter = mySharedPref.getScore();
            String[] numbers = mySharedPref.getNumbers();
            loadSavedNumbers(Arrays.asList(numbers));
            texScore.setText(String.valueOf(counter));
            texTime.setBase(SystemClock.elapsedRealtime() - pauseTime);
            texTime.start();
        } else {
            loadNumbersToButtons();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("PPP", "onPause");
        mediaPlayer.stop();
        pauseTime = SystemClock.elapsedRealtime() - texTime.getBase();
        texTime.stop();
        mySharedPref.setPlaying(true);
        musicTrue = mySharedPref.getMusic();
        if (mySharedPref.getPlaying()) {
            mediaPlayer.start();
        } else {
            mediaPlayer.stop();
        }
        mySharedPref.setScore(counter);
        mySharedPref.setPauseTime(pauseTime);
        texTime.start();
        StringBuilder numbers = new StringBuilder();
        for (int i = 0; i < containerButtons.getChildCount(); i++) {
            numbers.append(((Button) containerButtons.getChildAt(i)).getText().toString()).append("#");
        }
        mySharedPref.setNumbers(numbers.toString());
        pauseTime = mySharedPref.getPauseTime();
        texTime.setBase(SystemClock.elapsedRealtime() - pauseTime);
        texTime.start();
        musicTrue = mySharedPref.getClickBtn();
        texTime.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("PPP", "onStop");
        pauseTime = SystemClock.elapsedRealtime() - texTime.getBase();
        texTime.stop();
    }
}