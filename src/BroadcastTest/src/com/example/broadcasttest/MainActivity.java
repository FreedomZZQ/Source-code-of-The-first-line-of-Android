package com.example.broadcasttest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;



public class MainActivity extends Activity {
	
	
	class NetworkChangeReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent){
			ConnectivityManager connectionManager = (ConnectivityManager)
					getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
			if(networkInfo != null && networkInfo.isAvailable()){
				Toast.makeText(context, "newtwork available", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(context, "newtwork unavailable", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	class LocalReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent){
			Toast.makeText(context, "received local broadcast", Toast.LENGTH_SHORT).show();
		}
	}
	
	private IntentFilter intentFilter;
	private NetworkChangeReceiver networkChangeReceiver;
	private LocalReceiver localReceiver;
	private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        //intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //networkChangeReceiver = new NetworkChangeReceiver();
        //registerReceiver(networkChangeReceiver, intentFilter);
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		//Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
        		//sendOrderedBroadcast(intent, null);
        		//sendBroadcast(intent);
        		Intent intent = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
        		localBroadcastManager.sendBroadcast(intent);
        	}
        });
        
        intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	//unregisterReceiver(networkChangeReceiver);
    	localBroadcastManager.unregisterReceiver(localReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
