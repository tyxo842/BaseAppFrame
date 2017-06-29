package tyxo.baseappframe.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WifiAdmin {
    private final static String TAG = "tyxo WifiAdmin";

    private List<ScanResult> listResult;
    private ScanResult mScanResult;
    private WifiManager mWifiManager;
    private JSONObject jsonObject;
    private JSONArray array;
    WifiLock mWifiLock;
    private ArrayList<HashMap<String, String>> stepArray = new ArrayList<HashMap<String, String>>();

    private HashMap<String, String> map;

    public WifiAdmin(Context context) {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiManager.getConnectionInfo();
    }

    public void scan() {
        mWifiManager.startScan();
        listResult = mWifiManager.getScanResults();
        if (listResult != null) {

            return;
        } else {
        }
    }

    public void getScanResult() throws JSONException {
        if (jsonObject == null) {
            jsonObject = new JSONObject();
            array = new JSONArray();
        }
        scan();
        listResult = mWifiManager.getScanResults();
        if (listResult != null) {
            for (int i = 0; i < listResult.size(); i++) {
                mScanResult = listResult.get(i);

                map = new HashMap<String, String>();

                map.put("BSSID", mScanResult.BSSID);

                stepArray.add(map);
            }
            Gson gson = new Gson();
            String jsonStr = gson.toJson(stepArray);
            Log.e("tyxo", "==json数据" + jsonStr);
        }
    }
}
