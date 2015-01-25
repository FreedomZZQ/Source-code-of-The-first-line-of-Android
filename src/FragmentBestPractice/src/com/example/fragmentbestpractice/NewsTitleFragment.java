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
		news1.setTitle("央视转播亚洲杯遭外媒吐槽：落后得不可思议");
		news1.setContent("竟然还在用贴纸和白板 央视转播遭外媒吐槽 :就这点装备，都是找国家队临时借的\n本届亚洲杯足球赛，中央电视台体育频道全程转播，尤其是每逢中国队的比赛日，节目包装力度更大。不过央视的亚洲杯转播技术却被英国媒体《欧洲体育》狠狠地吐槽了一番，因为央视的转播中还在用贴纸和白板，这在他们看来都有些落后得不可思议了。\n《欧洲体育》的文章开头就开始吐槽，并且言辞非常犀利，“中国的中央电视台在报道亚洲杯的时候居然还在用贴纸和白板。随着科技的发展，体育赛事的报道也因此得到很大的改变，一些小发明、小创新被用到了这个行业。当然了，除非你是在中国。”\n文章随后将央视的转播和英国《天空体育》的转播做了对比，“这样的场景完全和《天空体育》的足球节目相反，在《天空体育》的节目中，卡拉格和加里·内维尔通过大型的触摸屏和一些小配件进行比赛的深度分析。”\n该文章最后还不忘调侃，“很奇怪，一个一年诞生成千上万的ipad和iphone用户的国家却在电视转播上不愿做出改变。”有趣的是，据了解，央视体育频道在转播中使用的这块白板都不是自己的，而是跟国家队借的。");
		newsList.add(news1);
		News news2 = new News();
		news2.setTitle("17名研究生违规转入985高校续：部分学生背景曝光");
		news2.setContent("新华网长沙1月24日电 全国211、985重点院校湖南大学近日被曝一次性接受17名外校研究生转入该校就读，被公众质疑或存在“转学腐败”。23日，湖南大学通报，决定撤销同意17名研究生转学的决定，并启动调查和追责程序。");
		newsList.add(news2);
		return newsList;
	}

}
