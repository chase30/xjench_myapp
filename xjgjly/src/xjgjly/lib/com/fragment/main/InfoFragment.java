package xjgjly.lib.com.fragment.main;

import java.net.URLEncoder;
import java.util.List;

import com.fax.utils.http.HttpUtils;
import com.fax.utils.task.ResultAsyncTask;
import com.google.gson.Gson;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import xjgjly.lib.com.MyApp;
import xjgjly.lib.com.MyFragment;
import xjgjly.lib.com.R;
import xjgjly.lib.com.model.DedeAddonarticleEntity;
import xjgjly.lib.com.views.MyTopBar;

/**
 * TODO
 * 大赛通知页卡 */

public class InfoFragment extends MyFragment{
	String json;
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		new ResultAsyncTask<DedeAddonarticleEntity>(context) {
			@Override
			protected DedeAddonarticleEntity doInBackground(Void... params) {
				String url =MyApp.Host;//TODO
				json=HttpUtils.reqForGet(url);
				try {
					return new Gson().fromJson(json, DedeAddonarticleEntity.class);
				} catch (Exception e) {
				}
				return null;
			}
			@Override
			protected void onPostExecuteSuc(DedeAddonarticleEntity dedea) {
				if(dedea.getBody()!=null){
					if(MyApp.OnCheck()==true){
/*						wv = (WebView) view.findViewById(R.id.webView);
						WebSettings settings=wv.getSettings();
						settings.setPluginState(PluginState.ON);
						settings.setJavaScriptCanOpenWindowsAutomatically(true);
						settings.setJavaScriptEnabled(true);
//						wv.setWebViewClient(new WebViewClientDemo());
						wv.loadUrl(dedea.getBody());*/
						      Intent intent=new Intent();
						      intent.setAction("android.intent.action.VIEW");
						      String urlen=URLEncoder.encode(dedea.getBody());
						      
						      Uri CONTENT_URI_BROWSERS = Uri.parse(MyApp.Host+"dedeArchivesController.do?videohtml&url="+urlen);
						      intent.setData(CONTENT_URI_BROWSERS);
						      intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
						      startActivity(intent);
					}else {MyApp.installFlashApkShowDialog(context);}
					backStack();
				}else{
					Toast.makeText(context, "暂记录", Toast.LENGTH_SHORT).show();
				}
			}
		}.setProgressDialog().execute();
		if(json==null){
		view = inflater.inflate(R.layout.info_ly_list, container, false);
		}else view = inflater.inflate(R.layout.home_ly_list_item_detail, container, false);
		return new MyTopBar(getActivity()).setTitle("大赛通知").setContentView(view);
	}
	
}
