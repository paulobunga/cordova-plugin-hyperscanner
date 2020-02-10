package cordova.plugin.hyperscanner;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;

import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import android.content.SharedPreferences;

/**
 * This class echoes a string called from JavaScript.
 */
public class Hyperscanner extends CordovaPlugin {

    private static final String ACTION_TRIGGER_DOWN = "registerKeyDown";
    private static final String ACTION_TRIGGER_UP = "registerKeyUp";

    private CallbackContext KEYUP_CALLBACK = null;
    private CallbackContext KEYDOWN_CALLBACK = null;
    private CallbackContext PUBLIC_CALLBACK = null;

    private BarcodeScanner barcodeScanner;
    private RFIDScanner rfidScanner;


    private View currentView = null;

    private Context ctx = null;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        Log.d("ISSSH", "Plugin has been initialised");

        ctx = cordova.getActivity().getApplicationContext();

        View.OnKeyListener listener = (view, keyCode, event) -> {
            Log.d("Hyperscanner", "The Key you pressed is: "+keyCode);

            return doKey(view, keyCode, event);
        };

        if (webView.getView() instanceof CustomCordovaSystemWebView){
            ((CustomCordovaSystemWebView)webView.getView()).setCustomOnKeyListener(listener);
        } else {
            webView.getView().setOnKeyListener(listener);
        }

        this.currentView = webView.getView();

    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        PUBLIC_CALLBACK = callbackContext;

        PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
        result.setKeepCallback(true);

        if (action.equalsIgnoreCase(ACTION_TRIGGER_DOWN)) {

            KEYDOWN_CALLBACK = callbackContext;
            return true;

        } else if (action.equalsIgnoreCase(ACTION_TRIGGER_UP)) {
            Log.d("Hyperscanner", "Action trigger up");
        }

        return true;
    }

    public void logResult(String body) {
        
        //Toast.makeText(cordova.getActivity(), body, Toast.LENGTH_SHORT).show();

    }



    public void startBarcodeScanner() {
        if(barcodeScanner ==  null) {
            barcodeScanner = new BarcodeScanner(cordova.getActivity(), new BarcodeScanner.OnScannerCallback() {
                @Override
                public void success(String barcode) {

                    logResult(barcode);

                    if(KEYDOWN_CALLBACK != null) {
                        PluginResult result = new PluginResult(PluginResult.Status.OK, barcode);
                        result.setKeepCallback(true);
                        KEYDOWN_CALLBACK.sendPluginResult(result);
                    } else {
                        Log.d("Hyperscanner", "Keydown Callback is null");
                    }
                }
    
                @Override
                public void error(String error) {
                    logResult(error);
                }
            });
        }
    }

    public void startRFIDScanner() {
        if (rfidScanner == null) {
            rfidScanner = new RFIDScanner(cordova.getActivity(), new RFIDScanner.OnScannerCallback() {
                @Override
                public void success(String rfidtag) {

                    logResult(rfidtag);
                    
                    if(KEYDOWN_CALLBACK != null) {
                        PluginResult result = new PluginResult(PluginResult.Status.OK, rfidtag);
                        result.setKeepCallback(true);
                        KEYDOWN_CALLBACK.sendPluginResult(result);
                    } else {
                        Log.d("ISSSH", "Keydown Callback is null");
                    }
                }
    
                @Override
                public void error(String error) {
                    logResult(error);
                }
            });
        }
    }


    //KEY EVENTS
    public boolean doKey(View v, int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_UP) {
            return KeyUp(keyCode, event);
        }
        else if (event.getAction() == KeyEvent.ACTION_DOWN) {
            return KeyDown(keyCode, event);
        }
        return false;
    }

    public boolean KeyDown(int keyCode, KeyEvent event) {
        if(keyCode == 280) {
            if(barcodeScanner != null && event.getRepeatCount() == 0) {
                barcodeScanner.startScan();
            } else {
                startBarcodeScanner();
            }
            return true;
        } else if(keyCode == 139) {
            if(rfidScanner != null && event.getRepeatCount() == 0) {
                rfidScanner.startScan("single");
            } else {
                startRFIDScanner();
            }
        }

        return false;
    }

    public boolean KeyUp(int keyCode, KeyEvent event) {
        if(keyCode == 280){
            if(barcodeScanner != null && event.getRepeatCount() == 0) {
                barcodeScanner.stopScan();
            }
            return true;
        } else if (keyCode == 139) {
            if(rfidScanner != null && event.getRepeatCount() == 0) {
                rfidScanner.stopScan();
            }
            return true;
        }

        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(barcodeScanner != null )
            barcodeScanner.destroy();

        if(rfidScanner != null)
            rfidScanner.destroy();
    }
}
