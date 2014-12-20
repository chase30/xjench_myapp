package xjgjly.lib.com.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import xjgjly.lib.com.MyFragment;
import xjgjly.lib.com.R;
import xjgjly.lib.com.views.MyTopBar;

/**
 * TODO
 * 大赛通知页卡 */

public class InfoFragment extends MyFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.info_ly_list, container, false);
		
		return new MyTopBar(getActivity()).setTitle("大赛通知").setContentView(view);
	}
}
