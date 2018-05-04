package com.example.layout;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by User on 1/14/2018.
 */

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast() {
        Toast.makeText(mContext, "Method Called from WebAppInterface", Toast.LENGTH_SHORT).show();
    }
}