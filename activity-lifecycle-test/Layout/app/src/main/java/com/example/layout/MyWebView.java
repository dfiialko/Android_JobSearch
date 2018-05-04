package com.example.layout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;


public class MyWebView extends AppCompatActivity{
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = findViewById(R.id.webview);
//        webView.loadUrl("https://www.google.com");

        String summary = "<html>" +
                "<input type=\"button\" value=\"Click Me\" onClick=\"showAndroidToast()\" />\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "    function showAndroidToast() {\n" +
                "        Interface.showToast();\n" +
                "    }\n" +
                "</script></html>";
        webView.loadData(summary, "text/html", null);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new WebAppInterface(this),"Interface");
    }


}
