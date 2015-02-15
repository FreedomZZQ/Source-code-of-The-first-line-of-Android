package com.example.networktest;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class ContentHandler extends DefaultHandler {
	private String nodeName;
	private StringBuilder id;
	private StringBuilder name;
	private StringBuilder version;
	@Override
	public void startDocument() throws SAXException{
		id = new StringBuilder();
		name = new StringBuilder();
		version = new StringBuilder();
	}
	@Override
	public void startElement(String uri, String localName, String qName, 
			Attributes attributes) throws SAXException{
		//��¼��ǰ�����
		nodeName = localName;
	}
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException{
		//���ݵ�ǰ�Ľ�����жϽ�������ӵ���һ��StringBuilder������
		if("id".equals(nodeName)){
			id.append(ch, start, length);
		}else if("name".equals(nodeName)){
			name.append(ch, start, length);
		}else if("version".equals(nodeName)){
			version.append(ch, start, length);
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws
	SAXException{
		if("app".equals(localName)){
			Log.d("hehe", "id is " + id.toString().trim());
			Log.d("hehe", "name is " + name.toString().trim());
			Log.d("hehe", "version is " + version.toString().trim());
			//���Ҫ��StringBuilder��յ�
			id.setLength(0);
			name.setLength(0);
			version.setLength(0);
		}
	}
	@Override
	public void endDocument() throws SAXException{
		
	}
}
