package cordova.plugin.hyperscanner;

import android.content.Context;

import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

public class CustomCordovaWebViewEngine extends SystemWebViewEngine {

    public CustomCordovaWebViewEngine(Context context, CordovaPreferences preferences) {

        this(new CustomCordovaSystemWebView(context), preferences);
    }

    public CustomCordovaWebViewEngine(SystemWebView webView) {
        super(webView);
    }

    public CustomCordovaWebViewEngine(SystemWebView webView, CordovaPreferences preferences) {
        super(webView, preferences);
        //Log.e("TAG", "Not a custom webview engine");
    }
}