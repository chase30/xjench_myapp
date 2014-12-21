package xjgjly.lib.com.fragment.main;

import java.io.Serializable;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import me.xiaopan.android.spear.sample.activity.DisplayActivity;
import me.xiaopan.android.spear.sample.activity.ViewPagerActivity;
import me.xiaopan.android.spear.sample.adapter.GridImageAdapter;
import me.xiaopan.android.spear.sample.fragment.GridFragment;
import me.xiaopan.android.spear.sample.fragment.ViewPagerFragment;

import com.fax.utils.http.HttpUtils;
import com.fax.utils.task.ResultAsyncTask;
import com.fax.utils.view.list.ObjectXListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import xjgjly.lib.com.MyApp;
import xjgjly.lib.com.MyFragment;
import xjgjly.lib.com.R;
import xjgjly.lib.com.model.ArchiveJson;
import xjgjly.lib.com.model.DedeArchivesEntity;
import xjgjly.lib.com.model.DedeUploadsEntity;
import xjgjly.lib.com.views.MyTopBar;

/**
 * 摄影作品页卡
 */

public class ImagesFragment extends MyFragment {
	public static final String PARAM_REQUIRED_STRING_ARRAY_URLS = "PARAM_REQUIRED_STRING_ARRAY_URLS";
	ArchiveJson imageuri;
	static final int MENU_SET_MODE = 0;
	private List<DedeArchivesEntity> mListItems;
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView mGridView;
	private GridImageAdapter mAdapter;
	private int page;
	int pageNo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.images_ly_list, container,
				false);
		mPullRefreshGridView = (PullToRefreshGridView) view
				.findViewById(R.id.pull_refresh_grid);
		mGridView = mPullRefreshGridView.getRefreshableView();
		mGridView.setBackgroundColor(Color.WHITE);
		mGridView.setPadding(2, 1, 2, 1);
		mGridView.setNumColumns(2);
		mGridView.setVerticalSpacing(20);
		mGridView.setHorizontalSpacing(5);
		page = 1;
		pageNo = 0;
		new getDataAync(context).setProgressDialog().execute();
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshGridView
				.setOnRefreshListener(new OnRefreshListener2<GridView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<GridView> refreshView) {
						if (page == pageNo) {
							Toast.makeText(context, "数据刷新完毕!",
									Toast.LENGTH_SHORT).show();
							mPullRefreshGridView.onRefreshComplete();
						} else {
							new getDataAync(context).execute();
							page++;
						}
					}

					@Override
					public void onPullUpToRefresh(
							
					PullToRefreshBase<GridView> refreshView) {
						if (page == pageNo) {
							Toast.makeText(context, "没有更多数据了!",
									Toast.LENGTH_SHORT).show();
							mPullRefreshGridView.onRefreshComplete();
						} else {
							new getDataAync(context).execute();
							page++;
						}
					}

				});

		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DedeArchivesEntity dedearch = mListItems.get(position);
				getsheyinlist(context, dedearch);
			}
		});
		return new MyTopBar(getActivity()).setTitle("摄影作品")
				.setContentView(view);
	}

	public void getsheyinlist(Context context, final DedeArchivesEntity dedearch) {
		new ResultAsyncTask<List<DedeUploadsEntity>>(context) {

			protected void onPostExecuteSuc(List<DedeUploadsEntity> result) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();
				bundle.putSerializable(
						ImagesFragment.PARAM_REQUIRED_STRING_ARRAY_URLS,
						(Serializable) result);
				bundle.putInt(
						ViewPagerFragment.PARAM_OPTIONAL_INT_CURRENT_POSITION,
						0);
				Intent intent = new Intent(getActivity(),
						ViewPagerActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);

			}

			protected List<DedeUploadsEntity> doInBackground(Void... params) {
				// TODO Auto-generated method stub
				String url = MyApp.Host
						+ "dedeArchivesController.do?getimage&id="
						+ dedearch.getId();
				String json = HttpUtils.reqForGet(url);
				List<DedeUploadsEntity> j = new Gson().fromJson(json,
						new TypeToken<List<DedeUploadsEntity>>() {
						}.getType());
				return j;
			}
		}.setProgressDialog().execute();
	}

	private class getDataAync extends ResultAsyncTask<ArchiveJson> {

		public getDataAync(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}

		protected void onPostExecuteSuc(ArchiveJson result) {
			// TODO Auto-generated method stub
			if (page == 1) {
				pageNo = result.getPageTotal();
				mListItems = result.getDedeArchivelist();
				mAdapter = new GridImageAdapter(context, mListItems, 2);
				mGridView.setAdapter(mAdapter);
			} else {
				mListItems.addAll(result.getDedeArchivelist());
				mAdapter.notifyDataSetChanged();
				mPullRefreshGridView.onRefreshComplete();
			}

		}

		protected ArchiveJson doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			String url = MyApp.Host
					+ "dedeArchivesController.do?getArchivelist&typeid=2&pageNo="
					+ page;
			String json = HttpUtils.reqForGet(url);
			ArchiveJson j = new Gson().fromJson(json, ArchiveJson.class);
			return j;
		}

	}

}
