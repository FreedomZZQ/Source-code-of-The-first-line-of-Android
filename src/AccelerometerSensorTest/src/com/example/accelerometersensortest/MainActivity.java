package com.example.accelerometersensortest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	if(sensorManager != null){
    		sensorManager.unregisterListener(listener);
    	}
    }
    
    private SensorEventListener listener = new SensorEventListener(){
    	@Override
    	public void onSensorChanged(SensorEvent event){
    		float xValue = Math.abs(event.values[0]);
    		float yValue = Math.abs(event.values[1]);
    		float zValue = Math.abs(event.values[2]);
    		if(xValue > 15 || yValue > 15 || zValue > 15){
    			Toast.makeText(MainActivity.this, "ҡһҡ", Toast.LENGTH_SHORT).show();
    		}
    	}
    	@Override
    	public void onAccuracyChanged(Sensor sensor, int accuracy){
    		
    	}
    	
    };


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
