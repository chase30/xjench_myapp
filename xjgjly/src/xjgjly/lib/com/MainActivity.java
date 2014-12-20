package xjgjly.lib.com;

import xjgjly.lib.com.fragment.main.HomeFragment;
import xjgjly.lib.com.fragment.main.ImagesFragment;
import xjgjly.lib.com.fragment.main.InfoFragment;
import xjgjly.lib.com.fragment.main.VideoFragment;

import com.fax.utils.view.RadioGroupFragmentBinder;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

public class MainActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final DrawerLayout drawerLayout = (DrawerLayout) View.inflate(this, R.layout.activity_main, null);
		setContentView(drawerLayout);
		//绑定底部按钮与页卡
				final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
				radioGroup.setOnCheckedChangeListener(new RadioGroupFragmentBinder(getSupportFragmentManager(), R.id.contain) {
					@Override
					public Fragment instanceFragment(int checkedId) {
						switch(checkedId){
						case R.id.bottom_ly_home: return new HomeFragment();
						case R.id.bottom_ly_video: return new VideoFragment();
						case R.id.bottom_ly_img: return new ImagesFragment();
						case R.id.bottom_ly_info: return new InfoFragment();
						}
						return null;
					}
				});
				((CompoundButton)radioGroup.findViewById(R.id.bottom_ly_home)).setChecked(true);
	}

}
