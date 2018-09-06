package com.dpi.confirmdevicecredential;

import android.app.KeyguardManager;
import android.content.Intent;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

public class ConfirmDeviceCredential extends CordovaPlugin {

    private KeyguardManager mKeyguardManager;
    private static final int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 1;
    public static CallbackContext mCallbackContext;

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        mCallbackContext = callbackContext;

        if (action.equals("confirm")) {
            mKeyguardManager = (KeyguardManager) cordova.getActivity().getSystemService(cordova.getActivity().getApplicationContext().KEYGUARD_SERVICE);

            showAuthScreen();
            return true;
        } else {
            return false;
        }
    }

    private void showAuthScreen() {
        Intent intent = mKeyguardManager.createConfirmDeviceCredentialIntent(null, null);
        if (intent != null) {
            cordova.setActivityResultCallback(this);
            cordova.getActivity().startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            if (resultCode == cordova.getActivity().RESULT_OK) {
                onAuthenticated();
            } else {
                onCancelled();
            }
        }
    }

    public void onAuthenticated() {
        mCallbackContext.success(1);
    }

    public void onCancelled() {
        mCallbackContext.success(0);
    }
}
