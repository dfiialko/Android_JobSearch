package com.example.a7a;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
/**
 * Created by User on 2/23/2018.
 */

public class Settings extends PreferenceActivity {

    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String appColor = sharedPref.getString("color","default");
        int appTheme = sharedPref.getInt("theme",0);
        Log.d("OnCreateTheme",Integer.toString(Constant.theme));
        if(appColor == "default" || appTheme == 0){
            setTheme(android.R.style.Theme_Light);
        }
        else{
            setTheme(appTheme);
        }
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();


    }

    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        Methods m;
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            Context hostActivity = getActivity();
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(hostActivity);
            editor = sharedPreferences.edit();
            m = new Methods();

        }

        @Override
        public void onResume() {
            super.onResume();
            // Set up a listener whenever a key changes

            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            // Set up a listener whenever a key changes
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

        }


        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            if (s.equals("listPref")) {
                ListPreference pref = (ListPreference) findPreference(s);
                String v = pref.getValue();
                Log.d("value", v);
                switch (v) {
                    case "1":
                        Constant.color = "Dark";
                        editor.putString("color","Dark");
                        editor.putInt("theme",android.R.style.Theme_Black);
                        break;
                    case "2":
                        Constant.color = "Light";
                        editor.putString("color","Light");
                        editor.putInt("theme",android.R.style.Theme_Light);
                        break;
                    case "3":
                        Constant.color = "Holo";
                        editor.putString("color","Holo");
                        editor.putInt("theme",android.R.style.Theme_Holo);
                        break;
                }

            }
            if(s.equals("sizePref")){
                ListPreference pref = (ListPreference) findPreference(s);
                String v = pref.getValue();
                Log.d("value", v);
                switch (v){
                    case "1":
                        editor.putInt("layout",R.layout.activity_listitem_large);
                        break;
                    case "2":
                        editor.putInt("layout",R.layout.activity_listitem);
                        break;
                    case "3":
                        editor.putInt("layout",R.layout.activity_listitem_small);
                        break;
                }
            }
            if(s.equals("links_checkbox_preference")){
                CheckBoxPreference pref = (CheckBoxPreference) findPreference("links_checkbox_preference");
                if(pref.isChecked()){
                    editor.putBoolean("saveLinks",true);
                }
                else {
                    editor.putBoolean("saveLinks",false);
                }
            }
            if(s.equals("notification_checkbox_preference")){
                CheckBoxPreference pref = (CheckBoxPreference) findPreference("notification_checkbox_preference");
                if(pref.isChecked()){
                    editor.putBoolean("notificationEnabled",true);
                }
                else{
                    editor.putBoolean("notificationEnabled",false);
                }
            }
            m.setColorTheme();
            editor.commit();
            Context c = getActivity();
            Intent intent = new Intent(c, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}



