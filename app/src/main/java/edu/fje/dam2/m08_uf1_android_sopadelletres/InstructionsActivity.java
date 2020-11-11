package edu.fje.dam2.m08_uf1_android_sopadelletres;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.GeolocationPermissions;
import android.webkit.WebViewClient;

public class InstructionsActivity extends AppCompatActivity {

    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        // Connecting UI
        browser = (WebView) findViewById(R.id.webkit);

        // Configuration for native webView on InstructionsActivity
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setAppCacheEnabled(true);
        browser.getSettings().setDatabaseEnabled(true);
        browser.getSettings().setDomStorageEnabled(true);

        // Enabling HTML5 geolocation
        browser.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        // Avoid using a browser like Chrome
        browser.setWebViewClient(new WebViewClient());
        // Load HTML file located on /src/main/assets
        browser.loadUrl("file:///android_asset/Instructions.html");

    }

}

