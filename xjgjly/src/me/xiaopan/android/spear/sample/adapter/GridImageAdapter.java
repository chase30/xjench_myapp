/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.xiaopan.android.spear.sample.adapter;

import java.util.List;

import xjgjly.lib.com.MyApp;
import xjgjly.lib.com.R;
import xjgjly.lib.com.model.ArchiveJson;
import xjgjly.lib.com.model.DedeArchivesEntity;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import me.xiaopan.android.spear.request.DisplayListener;
import me.xiaopan.android.spear.request.ProgressListener;
import me.xiaopan.android.spear.sample.DisplayOptionsType;
import me.xiaopan.android.spear.sample.widget.ProgressPieView;
import me.xiaopan.android.spear.util.FailureCause;
import me.xiaopan.android.spear.widget.SpearImageView;

public class GridImageAdapter extends BaseAdapter {
	private Context context;
	private  List<DedeArchivesEntity> dedeArchivelist;
	private int column;
	private int screenWidth;
	
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public GridImageAdapter(Context context, List<DedeArchivesEntity> dedeArchivelist, int column){
		this.context = context;
		this.dedeArchivelist = dedeArchivelist;
		this.column = column;
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2){
			screenWidth = display.getWidth();
		}else{
			Point point = new Point();
			display.getSize(point);
			screenWidth = point.x;
		}
	}

	@Override
	public Object getItem(int position) {
		return getimageuri(position);
	}

	@Override
	public int getCount() {
		return dedeArchivelist.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_image, parent, false);
            viewHolder.progressPieView = (ProgressPieView) convertView.findViewById(R.id.progress_gridItem_progress);
            viewHolder.progressPieView.setMax(100);
			viewHolder.image = (SpearImageView) convertView.findViewById(R.id.image_gridItem);
			viewHolder.writer=(TextView) convertView.findViewById(R.id.xj_writer);
			if(column > 1){
				viewHolder.image.setLayoutParams(new FrameLayout.LayoutParams(screenWidth/ column, (screenWidth/ column)/2));
			}
            viewHolder.image.setDisplayOptions(DisplayOptionsType.GRID_VIEW);
            viewHolder.image.setDisplayListener(new DisplayListener() {
                @Override
                public void onStarted() {
                    viewHolder.progressPieView.setVisibility(View.VISIBLE);
                    viewHolder.progressPieView.setProgress(0);
                }

                @Override
                public void onCompleted(String uri, ImageView imageView, BitmapDrawable drawable, From from) {
                    viewHolder.progressPieView.setVisibility(View.GONE);
                }

                @Override
                public void onFailed(FailureCause failureCause) {
                    viewHolder.progressPieView.setVisibility(View.GONE);
                }

                @Override
                public void onCanceled() {

                }
            });
            viewHolder.image.setProgressListener(new ProgressListener() {
                @Override
                public void onUpdateProgress(int totalLength, int completedLength) {
                    viewHolder.progressPieView.setProgress((int) (((float) completedLength / totalLength) * 100));
                }
            });
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}

        viewHolder.image.setImageByUriDiy(getimageuri(position), screenWidth/ column, (screenWidth/ column)/2);
        viewHolder.writer.setText(getWriter(position));
		return convertView;
	}
	
	class ViewHolder{
		SpearImageView image;
        ProgressPieView progressPieView;
        TextView writer;
	}
	public String getimageuri(int position){
	return	MyApp.AddUrl+dedeArchivelist.get(position).getLitpic();
	}
	
	public String getWriter(int position){
		return	dedeArchivelist.get(position).getTitle();
		}
	
}