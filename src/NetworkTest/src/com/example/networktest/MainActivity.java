package com.example.networktest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


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
    		//sendRequestWithHttpURLConnection();
    		sendRequestWithHttpClient();
    	}
    }
    
    private void sendRequestWithHttpClient(){
    	new Thread(new Runnable(){
    		@Override
    		public void run(){
    			try{
    				HttpClient httpClient = new DefaultHttpClient();
    				//HttpGet httpGet = new HttpGet("http://www.tekbroaden.com/get_data.xml");
    				HttpGet httpGet = new HttpGet("http://www.tekbroaden.com/get_data.json");
    				HttpResponse httpResponse = httpClient.execute(httpGet);
    				if(httpResponse.getStatusLine().getStatusCode() == 200){
    					HttpEntity entity = httpResponse.getEntity();
    					String response = EntityUtils.toString(entity, "utf-8");
    					//Message message = new Message();
    					//message.what = SHOW_RESPONSE;
    					//message.obj = response.toString();
    					//handler.sendMessage(message);
    					
    					//parseXMLWithPull(response);
    					
    					//parseXMLWithSAX(response);
    					
    					//parseJSONWithJSONObject(response);
    					
    					parseJSONWithGSON(response);
    				}
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    		}
    	}).start();
    }
    
    private void parseJSONWithGSON(String jsonData){
    	Gson gson = new Gson();
    	List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType());
    	for(App app : appList){
    		Log.d("hehe", "id is " + app.getId());
    		Log.d("hehe", "name is " + app.getName());
    		Log.d("hehe", "version is " + app.getVersion());
    	}
    }
    
    private void parseJSONWithJSONObject(String jsonData){
    	try{
    		JSONArray jsonArray = new JSONArray(jsonData);
    		for(int i = 0; i < jsonArray.length(); i++){
    			JSONObject jsonObject = jsonArray.getJSONObject(i);
    			String id = jsonObject.getString("id");
    			String name = jsonObject.getString("name");
    			String version = jsonObject.getString("version");
    			Log.d("hehe", "id is " + id);
    			Log.d("hehe", "name is " + name);
    			Log.d("hehe", "version is " + version);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    private void parseXMLWithSAX(String xmlData){
    	try{
    		SAXParserFactory factory = SAXParserFactory.newInstance();
    		XMLReader xmlReader = factory.newSAXParser().getXMLReader();
    		ContentHandler handler = new ContentHandler();
    		//将ContentHandler的实例设置到XMLReader中
    		xmlReader.setContentHandler(handler);
    		//开始执行解析
    		xmlReader.parse(new InputSource(new StringReader(xmlData)));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    private void parseXMLWithPull(String xmlData){
    	try{
    		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
    		XmlPullParser xmlPullParser = factory.newPullParser();
    		xmlPullParser.setInput(new StringReader(xmlData));
    		int eventType = xmlPullParser.getEventType();
    		String id = "";
    		String name = "";
    		String version = "";
    		while(eventType != XmlPullParser.END_DOCUMENT){
    			String nodeName = xmlPullParser.getName();
    			switch(eventType){
    			//开始解析某个节点
    			case XmlPullParser.START_TAG:{
    				if("id".equals(nodeName)){
    					id = xmlPullParser.nextText();
    				}else if("name".equals(nodeName)){
    					name = xmlPullParser.nextText();
    				}else if("version".equals(nodeName)){
    					version = xmlPullParser.nextText();
    				}
    				break;
    			}
    			case XmlPullParser.END_TAG:{
    				if("app".equals(nodeName)){
    					Log.d("hehe", "id is " + id);
    					Log.d("hehe", "name is " + name);
    					Log.d("hehe", "version is " + version);
    				}
    				break;
    			}
    			default:
    				break;
    			}
    			eventType = xmlPullParser.next();
    		}
    	}catch(Exception e){
    		e.printStackTrace();
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
