package com.example.slf.getgps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textGps;
    private ListView listMac;
    private LocationManager manager;
    private Location location;
    private ArrayList<Mac> macArrayList;
    private MacAdapter adapter;
    private String macString="";
    private String gpsString="";
    private String url="http://211.87.238.29";
    private Handler han;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getGps();
        getMac();
    }

    private void getMac() {
        Toast.makeText(MainActivity.this,"正在获取",Toast.LENGTH_SHORT).show();
        String wserviceName = Context.WIFI_SERVICE;
        WifiManager wm = (WifiManager) getSystemService(wserviceName);
        List<ScanResult> wifiList = wm.getScanResults();
        String text="";
        for (int i = 0; i < wifiList.size(); i++) {
            ScanResult result = wifiList.get(i);
            Log.d("dada", "bssid=" + result.BSSID+" ssid:"+result.SSID);
            text=text+"wifi名："+result.SSID+"\n"+"mac地址："+result.BSSID+"\n";
            Mac mac=new Mac("wifi名："+result.SSID,"mac地址："+result.BSSID);
            macArrayList.add(mac);
        }
        Log.d("test","text...."+text);
        macString=text;
        adapter=new MacAdapter(MainActivity.this,R.layout.mac_item,macArrayList,listMac);
        listMac.setAdapter(adapter);
    }

    private void getGps() {
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //判断GPS是否可用
        System.out.println("state=" + manager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        //检查权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //如果要用GPS就把下面的NETWORK_PROVIDER改成GPS_PROVIDER,但是GPS不稳定
        location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1, locationLinstener);
        textGps.setText("正在获取GPS定位信息。。。");

    }

    private void init() {
        macArrayList=new ArrayList<Mac>();
        listMac= (ListView) findViewById(R.id.list_mac);
        textGps= (TextView) findViewById(R.id.text_gps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        han=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String Jsmess =(String) msg.obj;
                Log.d("test", "Jsmess" + Jsmess);
                super.handleMessage(msg);
                if (Jsmess.equals("1")){
                    Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                }
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"正在上传信息",Toast.LENGTH_SHORT).show();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                AutoString autoString=new AutoString("gps",gpsString);
                autoString.addToResult("mac",macString);
                autoString.addToResult("name","沈立凡");
                autoString.addToResult("id","201400130024");
                String params=autoString.getResult();
                NetThread netThread =new NetThread(han,url,params);
                netThread.start();

            }
        });
    }
    LocationListener locationLinstener=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
    };
    private void showLocation(Location location) {
        String currentPosition=+location.getLatitude()+"    "+location.getLongitude();
        textGps.setText(currentPosition);
        gpsString=currentPosition;

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
