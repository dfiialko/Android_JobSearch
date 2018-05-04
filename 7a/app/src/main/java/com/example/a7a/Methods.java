package com.example.a7a;

import android.util.Log;

/**
 * Created by User on 2/23/2018.
 */

public class Methods {
    public void setColorTheme(){
    switch (Constant.color){
        case "Dark":
            Constant.theme = android.R.style.Theme_Black;
            Log.d("THEME","DARK");
            break;
        case "Light":
            Constant.theme = android.R.style.Theme_Light;
            break;
        case "Holo":
            Constant.theme = android.R.style.Theme_Holo;
            default:
                break;
    }
    }
}
