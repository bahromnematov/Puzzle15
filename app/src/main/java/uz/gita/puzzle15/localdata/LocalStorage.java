package uz.gita.puzzle15.localdata;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    private static LocalStorage localStorage;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    private LocalStorage(Context context) {
        preferences = context.getSharedPreferences("PUZZLE", Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    //    public static LocalStorage getInstance(Context context) {
//        if (localStorage == null) {
//            localStorage = new LocalStorage(context);
//        }
    public static LocalStorage getInstance() {
        return localStorage;
    }

    public static void init(Context context) {
        if (localStorage == null)
            localStorage = new LocalStorage(context);
    }

    void setPlaying(boolean trueOrFalse){
        editor.putBoolean("ISPLAYING",trueOrFalse);
        editor.apply();
    }

    boolean getPlaying(){
        return preferences.getBoolean("ISPLAYING",false);
    }


    void setString(String string){
        editor.putString("String",string).apply();
    }

    String getString(){
        return preferences.getString("String",null);
    }


    void setNumbers(String strings){
        editor.putString("strings",strings);
        editor.apply();
    }

    String[] getNumbers(){
        String[] str=preferences.getString("strings","").split("\\#");
        return str;
    }
}
