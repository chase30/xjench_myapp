package xjgjly.lib.com.fragment.main;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.android.spear.sample.adapter.ImageFragmentAdapter;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import xjgjly.lib.com.MyFragment;
import xjgjly.lib.com.R;
import xjgjly.lib.com.model.DedeUploadsEntity;
import xjgjly.lib.com.views.MyTopBar;

/**
 * 作品详情 */

public class imagedetailFragment extends MyFragment{
	public static final String PARAM_OPTIONAL_INT_CURRENT_POSITION = "PARAM_OPTIONAL_INT_CURRENT_POSITION";
	private List<DedeUploadsEntity>  uris;
	private ViewPager viewPager;
	private Handler handler;
	
	public imagedetailFragment(){
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		
		handler = new Handler();
		if(getArguments() != null){
			uris = (List<DedeUploadsEntity>) getArguments().getSerializable(ImagesFragment.PARAM_REQUIRED_STRING_ARRAY_URLS);
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		viewPager = new ViewPager(getActivity());
		viewPager.setId(R.id.viewPlayer);
		viewPager.setBackgroundColor(Color.BLACK);
		if(uris != null){
			viewPager.setAdapter(new ImageFragmentAdapter(getChildFragmentManager(), uris));
			viewPager.setCurrentItem(getArguments().getInt(PARAM_OPTIONAL_INT_CURRENT_POSITION, 0));
		}
		return new MyTopBar(getActivity()).setLeftBack().setTitle(uris.get(0).getTitle()).setContentView(viewPager);
	}
}
