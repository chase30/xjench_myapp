package xjgjly.lib.com.fragment.main;

import java.net.URLEncoder;
import java.util.List;

import me.xiaopan.android.spear.widget.SpearImageView;

import com.fax.utils.bitmap.BitmapManager;
import com.fax.utils.http.HttpUtils;
import com.fax.utils.task.ResultAsyncTask;
import com.google.gson.Gson;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import xjgjly.lib.com.MyApp;
import xjgjly.lib.com.MyFragment;
import xjgjly.lib.com.R;
import xjgjly.lib.com.model.DedeMegagameinfoEntity;
import xjgjly.lib.com.views.MyTopBar;

/**
 * TODO
 * 大赛通知页卡 */

public class InfoFragment extends MyFragment{
	String json;
	View view;
	TextView textview1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		
		view = inflater.inflate(R.layout.info_ly_list, container, false);
		new ResultAsyncTask<DedeMegagameinfoEntity>(context) {
			@Override
			protected DedeMegagameinfoEntity doInBackground(Void... params) {
				String url =MyApp.Host+"dedeArchivesController.do?getmessage";//TODO
				json=HttpUtils.reqForGet(url);
				if(json==null){
					return null;
				}
				try {
					return new Gson().fromJson(json, DedeMegagameinfoEntity.class);
				} catch (Exception e) {
				}
				return null;
			}
			@Override
			protected void onPostExecuteSuc(final DedeMegagameinfoEntity dedea) {
				if(dedea.getVideosrc().equals("")){
				    view.findViewById(R.id.wu).setVisibility(View.VISIBLE);
				}else{
					view.findViewById(R.id.you).setVisibility(View.VISIBLE);
					SpearImageView imageview=(SpearImageView) view.findViewById(R.id.img);
					BitmapManager.bindView(imageview,dedea.getImg());
					view.findViewById(R.id.you).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if(dedea!=null){
								if(InfoFragment.this.OnCheck()==true){
			/*						wv = (WebView) view.findViewById(R.id.webView);
									WebSettings settings=wv.getSettings();
									settings.setPluginState(PluginState.ON);
									settings.setJavaScriptCanOpenWindowsAutomatically(true);
									settings.setJavaScriptEnabled(true);
//									wv.setWebViewClient(new WebViewClientDemo());
									wv.loadUrl(dedea.getBody());*/
									
									
									      String urlen=URLEncoder.encode(dedea.getVideosrc());
									      Uri CONTENT_URI_BROWSERS = Uri.parse(MyApp.Host+"dedeArchivesController.do?videohtml&url="+urlen);
									      Intent  intent = new  Intent(Intent.ACTION_VIEW, CONTENT_URI_BROWSERS);
									      startActivity(intent);
								}else {MyApp.installFlashApkShowDialog(context);}
							
							}else{
								Toast.makeText(context, "大赛通知暂时还没公布，敬请期待", Toast.LENGTH_SHORT).show();
							}
						}
					});
					
				}
				
			}
		}.setProgressDialog().execute();
		
		return new MyTopBar(getActivity()).setTitle("大赛通知").setContentView(view);
	}
	
	private  boolean hasAdobePlayer = false;// ADOBE FLASH PLAYER插件安装状态
	public  boolean OnCheck() {
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
