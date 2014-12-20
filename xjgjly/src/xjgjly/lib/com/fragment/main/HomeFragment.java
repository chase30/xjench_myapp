package xjgjly.lib.com.fragment.main;

import java.util.List;

import me.xiaopan.android.spear.widget.SpearImageView;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.view.list.ObjectXAdapter;
import com.fax.utils.view.list.ObjectXListView;
import com.google.gson.Gson;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import xjgjly.lib.com.MyApp;
import xjgjly.lib.com.MyFragment;
import xjgjly.lib.com.R;
import xjgjly.lib.com.model.DedeArchivesEntityList;
import xjgjly.lib.com.model.DedeArchivesEntityList.DedeArchivesEntity;

/**
 * TODO
 * 首页 页卡 */

public class HomeFragment extends MyFragment{
	public static final int Request_Home = 1;
	public static final String TAG ="HomeFragment";
	private ImageView img;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.home_ly_list, container, false);
		img=(ImageView) view.findViewById(R.id.home_ly_list_item_logoimg);
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			img.getLayoutParams().height = getResources().getDisplayMetrics().widthPixels * 300 / 612;
		}else{
			img.getLayoutParams().width = getResources().getDisplayMetrics().heightPixels /2 * 612 / 300;
		}
		if(MyApp.isNetworkConnected(context)==false){
			MyApp.isNetworkConnectedShowDialog(context);
		}
		ObjectXListView listView = (ObjectXListView) view.findViewById(android.R.id.list);
		listView.setPullRefreshEnable(false);
		 listView.setAdapter(new ObjectXAdapter.PagesAdapter<DedeArchivesEntity>() {
			
			 @Override
			 public String getUrl(int page) {
				 return MyApp.Host+"dedeArchivesController.do?getArchivelist&typeid=3&pageNo="+page;
			 }

			@Override
			public View bindView(DedeArchivesEntity de, int position, View convertView) {
				 if(convertView== null){
	                	convertView = View.inflate(context, R.layout.home_ly_list_item, null);
	                }
				 if(!de.getLitpic().equals("")&&!de.getTitle().equals("")){
					 SpearImageView imageview=(SpearImageView) convertView.findViewById(R.id.home_ly_list_item_img);
//					 imageview.setImageByUri(MyApp.AddUrl+de.getLitpic());
					 BitmapManager.bindView(imageview,MyApp.AddUrl+de.getLitpic());
					 ((TextView)convertView.findViewById(R.id.home_ly_list_item_name)).setText(de.getTitle());
				 }
				return convertView;
			}
			@Override
			public void onItemClick(DedeArchivesEntity de, View view, int position, long id) {
				super.onItemClick(de, view, position, id);
				HomeDetailFragment hdf=new HomeDetailFragment();
				hdf.setArguments(MyApp.createIntent(de).getExtras());
				hdf.setTargetFragment(HomeFragment.this, Request_Home);
				addFragment(hdf);
			}
			@Override
			public List<DedeArchivesEntity> instanceNewList(String json) throws Exception {
				return new Gson().fromJson(json, DedeArchivesEntityList.class).getData();
			}
		});
		
		
		return view;
	}
	
}
