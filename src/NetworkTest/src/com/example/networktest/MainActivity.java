package com.example.networktest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {
	public static final int SHOW_RESPONSE = 0;
	private Button sendRequest;
	private TextView responseText;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case SHOW_RESPONSE:
				String response = (String) msg.obj;
				responseText.setText(response);
			}
		}
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendRequest = (Button)findViewById(R.id.send_request);
        responseText = (TextView)findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
    	if(v.getId() == R.id.send_request){
    		sendRequestWithHttpURLConnection();
    	}
    }
    
    private void sendRequestWithHttpURLConnection(){
    	new Thread(new Runnable(){
    		@Override
    		public void run(){
    			HttpURLConnection connection = null;
    			try{
    				URL url = new URL("http://www.baidu.com");
    				connection = (HttpURLConnection) url.openConnection();
    				connection.setRequestMethod("GET");
    				connection.setConnectTimeout(8000);
    				connection.setReadTimeout(8000);
    				InputStream in = connection.getInputStream();
    				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    				StringBuilder response = new StringBuilder();
    				String line;
    				while((line = reader.readLine()) != null){
    					response.append(line);
    				}
    				Message message = new Message();
    				message.what = SHOW_RESPONSE;
    				message.obj = response.toString();
    				handler.sendMessage(message);
    			}catch(Exception e){
    				e.printStackTrace();
    			}finally{
    				if(connection != null){
    					connection.disconnect();
    				}
    			}
    		}
    	}).start();
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
