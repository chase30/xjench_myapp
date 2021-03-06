package xjgjly.lib.com;


import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragment extends Fragment {
	public Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//防止Fragment在add的时候没有背景色叠加显示出错和touch事件传递底部
		View view = new View(context);
		view.setClickable(true);
		view.setBackgroundResource(R.color.window_bg);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if(view != null){//防止Fragment在add的时候没有背景色叠加显示出错和touch事件传递底部
			view.setClickable(true);
			view.setBackgroundResource(R.color.window_bg);
		}
	}
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T getSerializableExtra(Class<T> c){
		if(getArguments()==null) return null;
		return (T) getArguments().getSerializable(c.getName());
	}

	public void startFragment(Fragment fragment){
		FragmentContain.start(getActivity(), fragment);
	}
	public void startFragment(Class<? extends Fragment> c, Bundle bundle, int request){
		FragmentContain.start(getActivity(), c, bundle, request);
	}
	public void addFragment(Fragment fragment){
		getFragmentManager().beginTransaction().add(R.id.contain, fragment, fragment.getClass().getName()).addToBackStack(null).commit();
	}
	public void replaceFragment(Fragment fragment){
		getFragmentManager().beginTransaction().replace(R.id.contain, fragment, fragment.getClass().getName()).commit();
	}
	public void addFragment(FragmentTransaction ft, Fragment fragment){
		ft.add(R.id.contain, fragment, fragment.getClass().getName()).addToBackStack(null);
	}
	
	public void backStack(){
		if(context instanceof FragmentActivity){
			if(!((FragmentActivity) context).getSupportFragmentManager().popBackStackImmediate()){
				((Activity) context).finish();
			}
		}else if(context instanceof Activity){
			((Activity) context).finish();
		}
	}

}
