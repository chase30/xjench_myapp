package xjgjly.lib.com.fragment.main;
import java.util.List;

import me.xiaopan.android.spear.widget.SpearImageView;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.view.list.ObjectXAdapter;
import com.fax.utils.view.list.ObjectXListView;
import com.google.gson.Gson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import xjgjly.lib.com.MyApp;
import xjgjly.lib.com.MyFragment;
import xjgjly.lib.com.R;
import xjgjly.lib.com.model.DedeArchivesEntityList;
import xjgjly.lib.com.model.DedeArchivesEntityList.DedeArchivesEntity;
import xjgjly.lib.com.views.MyTopBar;

/**
 * TODO
 * 微电影页卡*/

public class VideoFragment extends MyFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.video_ly_list, container, false);
		
//		ObjectXListView listView = new ObjectXListView(context);
		ObjectXListView listView = (ObjectXListView) view.findViewById(android.R.id.list);
		listView.setPullRefreshEnable(false);
		 listView.setAdapter(new ObjectXAdapter.PagesAdapter<DedeArchivesEntity>() {
			
			 @Override
			 public String getUrl(int page) {
				 return MyApp.Host+"dedeArchivesController.do?getArchivelist&typeid=4&pageNo="+page;
			 }

			@Override
			public View bindView(DedeArchivesEntity de, int position, View convertView) {
				 if(convertView== null){
	                	convertView = View.inflate(context, R.layout.video_ly_list_item, null);
	                }
				 if(!de.getLitpic().equals("")&&!de.getTitle().equals("")){
					 SpearImageView imageview=(SpearImageView) convertView.findViewById(R.id.video_ly_list_item_img);
//					 imageview.setImageByUri(MyApp.AddUrl+de.getLitpic());
					 BitmapManager.bindView(imageview,MyApp.AddUrl+de.getLitpic());
					 ((TextView)convertView.findViewById(R.id.video_ly_list_item_name)).setText(de.getTitle());
				 }
				return convertView;
			}
			@Override
			public void onItemClick(DedeArchivesEntity de, View view, int position, long id) {
				super.onItemClick(de, view, position, id);
				addFragment(MyApp.createFragment(HomeDetailFragment.class, de));
			}
			@Override
			public List<DedeArchivesEntity> instanceNewList(String json) throws Exception {
				return new Gson().fromJson(json, DedeArchivesEntityList.class).getData();
			}
		});
		
		return new MyTopBar(getActivity()).setTitle("微电影").setContentView(view);
	}
}
