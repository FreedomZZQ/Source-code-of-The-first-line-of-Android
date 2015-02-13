package com.example.servicetest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener{
	
	private Button startService;
	private Button stopService;
	private Button bindService;
	private Button unbindService;
	private Button startIntentService;
	private MyService.DownloadBinder downloadBinder;
	private ServiceConnection connection = new ServiceConnection(){
		@Override
		public void onServiceDisconnected(ComponentName name){
			
		}
		@Override
		public void onServiceConnected(ComponentName name, IBinder service){
			downloadBinder = (MyService.DownloadBinder) service;
			downloadBinder.startDownload();
			downloadBinder.getProgress();
		}
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService = (Button) findViewById(R.id.start_service);
        stopService = (Button) findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        
        bindService = (Button) findViewById(R.id.bind_service);
        bindService.setOnClickListener(this);
        unbindService = (Button) findViewById(R.id.unbind_service);
        unbindService.setOnClickListener(this);
        startIntentService = (Button) findViewById(R.id.start_intent_service);
        startIntentService.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v){
    	switch(v.getId()){
    	case R.id.start_service:
    		Intent startIntent = new Intent(this, MyService.class);
    		startService(startIntent);
    		break;
    	case R.id.stop_service:
    		Intent stopIntent = new Intent(this, MyService.class);
    		stopService(stopIntent);
    		break;
    	case R.id.bind_service:
    		Intent bindIntent = new Intent(this, MyService.class);
    		bindService(bindIntent, connection, BIND_AUTO_CREATE);
    		break;
    	case R.id.unbind_service:
    		unbindService(connection);
    		break;
    	case R.id.start_intent_service:
    		Log.d("hehe", "Thread id is " + Thread.currentThread().getId());
    		Intent intentService = new Intent(this, MyIntentService.class);
    		startService(intentService);
    		break;
    	default:
    		break;
    	}
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
