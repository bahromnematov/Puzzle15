package uz.gita.puzzle15.localdata;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPref {
    private static MySharedPref localStorage;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    private MySharedPref(Context context) {
        preferences = context.getSharedPreferences("PUZZLE", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    //    public static LocalStorage getInstance(Context context) {
//        if (localStorage == null) {
//            localStorage = new LocalStorage(context);
//        }
    public static MySharedPref getInstance() {
        return localStorage;
    }

    public static void init(Context context) {
        if (localStorage == null)
            localStorage = new MySharedPref(context);
    }

    public void setPlaying(boolean trueOrFalse){
        editor.putBoolean("ISPLAYING",trueOrFalse);
        editor.apply();
    }

    public boolean getPlaying(){
        return preferences.getBoolean("ISPLAYING",false);
    }

    public void setNumbers(String strings){
        editor.putString("strings",strings);
        editor.apply();
    }

   public String[] getNumbers(){
        String[] str=preferences.getString("strings","").split("\\#");
        return str;
    }
    public void setScore(int score){
        editor.putInt("SCORE",score);
        editor.apply();
    }

    public int getScore(){
        return preferences.getInt("SCORE",0);
    }

    public void setPauseTime(long pauseTime){
        editor.putLong("PAUSETIME",pauseTime);
        editor.apply();
    }

    public long getPauseTime(){
        return preferences.getLong("PAUSETIME",0);
    }

    public void setRecord1(int count){
        editor.putInt("COUNT",count);
        editor.apply();
    }

    public int getRecord1(){
        return preferences.getInt("COUNT",0);
    }

    public void setTime(long count){
        editor.putLong("TIME",count);
        editor.apply();
    }

    public int getTime(){
        return preferences.getInt("TIME",0);
    }

    public void setIsTrue(boolean isTrueMusic){
        editor.putBoolean("MUSICTRUE",isTrueMusic);
        editor.apply();
    }

    public boolean getIsTrue(){
        return preferences.getBoolean("MUSICTRUE",false);
    }

    public void setMusic(boolean IsTrueMusic){
        editor.putBoolean("Music",IsTrueMusic).apply();
    }

    public boolean getMusic(){
        return preferences.getBoolean("Music",false);
    }


    public void setClickBtn(boolean isClick){
        editor.putBoolean("ISTRUE",isClick);
        editor.apply();
    }

    public boolean getClickBtn(){
        return preferences.getBoolean("ISTRUE",true);
    }

}


