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
import android.widget.Toast;

import android.content.SharedPreferences;

/**
 * This class echoes a string called from JavaScript.
 */
public class Hyperscanner extends CordovaPlugin {

    private static final String ACTION_TRIGGER_DOWN = "registerKeyDown";
    private static final String ACTION_TRIGGER_UP = "registerKeyUp";
    private static final String ACTION_START_RFID = "startRFIDScanner";
    private static final String ACTION_STOP_RFID = "stopRFIDScanner";
    private static final String ACTION_START_BARCODE = "startBarcodeScanner";
    private static final String ACTION_STOP_BARCODE = "stopBarcodeScanner";

    private CallbackContext KEYUP_CALLBACK = null;
    private CallbackContext KEYDOWN_CALLBACK = null;
    private CallbackContext PUBLIC_CALLBACK = null;

    private BarcodeScanner barcodeScanner;
    private RFIDScanner rfidScanner;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        
        pref = cordova.getActivity().getSharedPreferences("BIGBOXAFRICA", Context.MODE_PRIVATE);
        editor = pref.edit();

    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        PUBLIC_CALLBACK = callbackContext;

        PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
        result.setKeepCallback(true);

        if (action.equalsIgnoreCase(ACTION_TRIGGER_DOWN)) {
            Log.d("Hyperscanner", "Action trigger down");
        } else if (action.equalsIgnoreCase(ACTION_TRIGGER_UP)) {
            Log.d("Hyperscanner", "Action trigger up");
        } else if (action.equalsIgnoreCase(ACTION_START_RFID)) {
            Log.d("Hyperscanner", "Action start RFID");
        } else if (action.equalsIgnoreCase(ACTION_STOP_RFID)) {
            Log.d("Hyperscanner", "Action stop RFID");
        } else if (action.equalsIgnoreCase(ACTION_START_BARCODE)) {
            Log.d("Hyperscanner", "Action start Barcode Scanner");
        } else if (action.equalsIgnoreCase(ACTION_STOP_BARCODE)) {
            Log.d("Hyperscanner", "Action stop Barcode scanner");
        }

        return true;
    }

    public void logResult(String body) {
        
        Toast.makeText(cordova.getActivity(), body, Toast.LENGTH_SHORT).show();

        PluginResult result = new PluginResult(PluginResult.Status.OK, body);
        result.setKeepCallback(true);

    }

    public void startBarcodeScanner() {
        if(barcodeScanner ==  null) {
            barcodeScanner = new BarcodeScanner(cordova.getActivity(), new BarcodeScanner.OnScannerCallback() {
                @Override
                public void success(String barcode) {
                    logResult(barcode);
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
                }
    
                @Override
                public void error(String error) {
                    logResult(error);
                }
            });
        }
    }

    public void stopRFIDScanner() {

    }

    public void stopBarcodeScanner() {

    }
}
