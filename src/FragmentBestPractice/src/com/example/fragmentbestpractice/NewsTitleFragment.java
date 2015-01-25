package com.example.fragmentbestpractice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NewsTitleFragment extends Fragment implements OnItemClickListener{
	private ListView newsTitleListView;
	private List<News> newsList;
	private NewsAdapter adapter;
	private boolean isTwoPane;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		newsList = getNews();
		adapter = new NewsAdapter(activity, R.layout.news_item, newsList);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.news_title_frag, container, false);
		newsTitleListView = (ListView) view.findViewById(R.id.news_title_list_view);
		newsTitleListView.setAdapter(adapter);
		newsTitleListView.setOnItemClickListener(this);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		if(getActivity().findViewById(R.id.news_content_layout) != null){
			isTwoPane = true;
		}else{
			isTwoPane = false;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
		News news = newsList.get(position);
		
		if(isTwoPane){
			NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
			newsContentFragment.refresh(news.getTitle(), news.getContent());
		}else{
			
			NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());
			
		}
	}
	
	private List<News> getNews(){
		List<News> newsList = new ArrayList<News>();
		News news1 = new News();
		news1.setTitle("����ת�����ޱ�����ý�²ۣ����ò���˼��");
		news1.setContent("��Ȼ��������ֽ�Ͱװ� ����ת������ý�²� :�����װ���������ҹ��Ҷ���ʱ���\n�������ޱ����������������̨����Ƶ��ȫ��ת����������ÿ���й��ӵı����գ���Ŀ��װ���ȸ��󡣲������ӵ����ޱ�ת������ȴ��Ӣ��ý�塶ŷ���������ݺݵ��²���һ������Ϊ���ӵ�ת���л�������ֽ�Ͱװ壬�������ǿ�������Щ���ò���˼���ˡ�\n��ŷ�������������¿�ͷ�Ϳ�ʼ�²ۣ������ԴǷǳ�Ϭ�������й����������̨�ڱ������ޱ���ʱ���Ȼ��������ֽ�Ͱװ塣���ſƼ��ķ�չ���������µı���Ҳ��˵õ��ܴ�ĸı䣬һЩС������С���±��õ��������ҵ����Ȼ�ˣ������������й�����\n����������ӵ�ת����Ӣ���������������ת�����˶Աȣ��������ĳ�����ȫ�͡�����������������Ŀ�෴���ڡ�����������Ľ�Ŀ�У�������ͼ����ά��ͨ�����͵Ĵ�������һЩС������б�������ȷ�������\n��������󻹲�����٩��������֣�һ��һ�굮����ǧ�����ipad��iphone�û��Ĺ���ȴ�ڵ���ת���ϲ�Ը�����ı䡣����Ȥ���ǣ����˽⣬��������Ƶ����ת����ʹ�õ����װ嶼�����Լ��ģ����Ǹ����Ҷӽ�ġ�");
		newsList.add(news1);
		News news2 = new News();
		news2.setTitle("17���о���Υ��ת��985��У��������ѧ�������ع�");
		news2.setContent("�»�����ɳ1��24�յ� ȫ��211��985�ص�ԺУ���ϴ�ѧ���ձ���һ���Խ���17����У�о���ת���У�Ͷ������������ɻ���ڡ�תѧ���ܡ���23�գ����ϴ�ѧͨ������������ͬ��17���о���תѧ�ľ����������������׷�����");
		newsList.add(news2);
		return newsList;
	}

}
