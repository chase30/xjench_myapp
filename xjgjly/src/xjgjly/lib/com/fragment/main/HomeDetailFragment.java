package xjgjly.lib.com.fragment.main;
import java.net.URLEncoder;
import java.util.List;
import com.fax.utils.http.HttpUtils;
import com.fax.utils.task.ResultAsyncTask;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import xjgjly.lib.com.MyApp;
import xjgjly.lib.com.MyFragment;
import xjgjly.lib.com.R;
import xjgjly.lib.com.model.DedeAddonarticleEntity;
import xjgjly.lib.com.model.DedeArchivesEntityList.DedeArchivesEntity;
import xjgjly.lib.com.views.MyTopBar;

/**
 * TODO
 * 旅游广告页卡*/

@SuppressLint("SetJavaScriptEnabled")
public class HomeDetailFragment extends MyFragment{
	WebView wv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.home_ly_list_item_detail, container, false);
		final DedeArchivesEntity d = getSerializableExtra(DedeArchivesEntity.class);
		new ResultAsyncTask<DedeAddonarticleEntity>(context) {
			@Override
			protected DedeAddonarticleEntity doInBackground(Void... params) {
				String url =MyApp.Host+"dedeArchivesController.do?getmovie&id="+d.getId();
				String json=HttpUtils.reqForGet(url);
				try {
					return new Gson().fromJson(json, DedeAddonarticleEntity.class);
				} catch (Exception e) {
				}
				return null;
			}
			@Override
			protected void onPostExecuteSuc(DedeAddonarticleEntity dedea) {
				if(dedea.getBody()!=null){
					if(OnCheck()==true){
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
		if(getTargetFragment() instanceof HomeFragment){
		  return new MyTopBar(getActivity()).setLeftBack().setTitle("旅游广告").setContentView(view);
		}else{
			return new MyTopBar(getActivity()).setLeftBack().setTitle("微电影").setContentView(view);
		}
	}
	
	
	private class WebViewClientDemo extends WebViewClient {  
        @Override  
        // 在WebView中而不是默认浏览器中显示页面  
        public boolean shouldOverrideUrlLoading(WebView view, String url) {  
            view.loadUrl(url);  
            return true;  
        }  
    }  
	/**
	 * 判断是否安装ADOBE FLASH PLAYER插件
	 * 
	 * @return
	 */
	private boolean hasAdobePlayer = false;// ADOBE FLASH PLAYER插件安装状态
	public boolean OnCheck() {
		// 判断是否安装ADOBE FLASH PLAYER插件
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> lsPackageInfo = pm.getInstalledPackages(0);

		for (PackageInfo pi : lsPackageInfo) {
			if (pi.packageName.contains("com.adobe.flashplayer")) {
				hasAdobePlayer = true;
				break;
			}
		}
		// 如果插件安装一切正常
		if (hasAdobePlayer == true) {
			return true;
		} else {
			return false;
		}
	}
}
